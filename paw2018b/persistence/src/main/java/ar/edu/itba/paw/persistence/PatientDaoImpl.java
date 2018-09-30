package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) {
        final Map<String, Object> args = new HashMap<>();
        args.put("firstname", firstName);
        args.put("lastname", lastName);
        args.put("phonenumber", phoneNumber);
        args.put("email", email);
        args.put("password", password);
        final Number reviewId = jdbcInsert.executeAndReturnKey(args);
        return new Patient(new Integer(reviewId.intValue()), firstName, lastName, phoneNumber, email, password);
    }

    public Boolean setDoctorId(Integer patientId, Integer doctorId){

        if( jdbcTemplate.update("UPDATE patient set doctorId = ? where id = ?", doctorId, patientId) == 1 ){
            return true;
        }

        return false;

    }

    @Override
    public Optional<Patient> findPatientById(Integer id) {
        final List<Patient> list = jdbcTemplate.query("SELECT * FROM patient WHERE id = ?", ROW_MAPPER, id);

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }

    @Override
    public Optional<Patient> findPatientByEmail(String email) {

        final List<Patient> list = jdbcTemplate.query("SELECT * FROM patient WHERE email = ?", ROW_MAPPER, email);

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }

}
