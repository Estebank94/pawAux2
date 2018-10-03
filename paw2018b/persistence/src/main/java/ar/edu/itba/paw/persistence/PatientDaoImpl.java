package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class PatientDaoImpl implements PatientDao {

    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<Patient> ROW_MAPPER = (rs, rowNum) ->
            new Patient(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
                    rs.getString("phoneNumber"), rs.getString("email"), rs.getString("password"),
                    rs.getInt("doctorId") );

    @Autowired
    public PatientDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("patient")
                .usingGeneratedKeyColumns("id")
                .usingColumns( "firstname", "lastname", "phonenumber", "email", "password", "doctorId" );
    }

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException {
        final Map<String, Object> args = new HashMap<>();
        args.put("firstname", firstName);
        args.put("lastname", lastName);
        args.put("phonenumber", phoneNumber);
        args.put("email", email);
        args.put("password", password);
        Patient patient = null;
        try {
            final Number reviewId = jdbcInsert.executeAndReturnKey(args);
            patient = new Patient(new Integer(reviewId.intValue()), firstName, lastName, phoneNumber, email, password);
        }catch (DuplicateKeyException exc1){
            throw new RepeatedEmailException();
        }
        return patient;
    }

    public Boolean setDoctorId(Integer patientId, Integer doctorId) throws NotCreatePatientException {
        int ans;
        try {
            ans = jdbcTemplate.update("UPDATE patient set doctorId = ? where id = ?", doctorId, patientId);
        } catch (Exception e){
            throw new NotCreatePatientException();
        }
        if (ans == 1){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Optional<Patient> findPatientById(Integer id) {
        final List<Patient> list = jdbcTemplate.query("SELECT * FROM patient WHERE id = ?", ROW_MAPPER, id);

        if (list.isEmpty()) {
            return Optional.empty();
        }
        list.get(0).setAppointments(findPacientAppointmentsById(list.get(0).getPatientId()));
        return Optional.of(list.get(0));
    }

    @Override
    public Optional<Patient> findPatientByEmail(String email) {

        final List<Patient> list = jdbcTemplate.query("SELECT * FROM patient WHERE email = ?", ROW_MAPPER, email);

        if (list.isEmpty()) {
            return Optional.empty();
        }
        list.get(0).setAppointments(findPacientAppointmentsById(list.get(0).getPatientId()));

        return Optional.of(list.get(0));
    }

    private static final RowMapper<Appointment> ROW_MAPPER_APPOINTMENT = (rs, rowNum) -> new Appointment(
            LocalDate.parse(rs.getString("appointmentday")) ,
            LocalTime.parse(rs.getString("appointmenttime")),
            Integer.valueOf(rs.getInt("doctorId")),
            rs.getString("firstname"),
            rs.getString("lastname"),
            rs.getString("phoneNumber"),
            Integer.valueOf(rs.getInt("clientId")),
            rs.getString("address"));


    private Set<Appointment> findPacientAppointmentsById(Integer id){
        Set<Appointment> appointments = new HashSet<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT appointment.doctorId, appointment.clientId, appointmentDay, appointmentTime, doctor.firstname , doctor.lastname, doctor.phoneNumber,doctor.address ")
                .append("FROM doctor ")
                .append("JOIN appointment ON doctor.id = appointment.doctorId ")
                .append("JOIN patient ON appointment.clientid = patient.id ")
                .append("WHERE patient.id = ?");

        List<Appointment> appointmentsList = jdbcTemplate.query(query.toString(), ROW_MAPPER_APPOINTMENT,id);
        for (Appointment appointmentIterator: appointmentsList){
            appointments.add(appointmentIterator);
        }
        return appointments;
    }

}
