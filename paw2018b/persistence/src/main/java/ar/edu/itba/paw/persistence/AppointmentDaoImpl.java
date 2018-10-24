package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AppointmentDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.exceptions.RepeatedAppointmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public AppointmentDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("appointment")
                .usingColumns("doctorId","clientid","appointmentDay", "appointmentTime","identifier")
        .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Appointment> createAppointment(Integer doctorId, Integer clientId, LocalDate appointmentDay, LocalTime appointmentTime) throws RepeatedAppointmentException{
        final Map<String,Object> entry = new HashMap<>();
        StringBuilder identifier = new StringBuilder();
        identifier.append(appointmentDay.toString())
                .append(appointmentTime.toString())
                .append(doctorId);

        entry.put("doctorId",doctorId);
        entry.put("clientId",clientId);
        entry.put("appointmentDay",appointmentDay);
        entry.put("appointmentTime",appointmentTime);
        entry.put("identifier",identifier.toString());
        Number appointmentId = null;
        try {
            appointmentId = jdbcInsert.executeAndReturnKey(entry);
        } catch (DuplicateKeyException ex1){
            throw new RepeatedAppointmentException();
        }
        return Optional.ofNullable(new Appointment(appointmentDay, appointmentTime));
    }
}
