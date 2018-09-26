package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PatientDaoImpl implements PatientDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public PatientDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("patient")
                .usingGeneratedKeyColumns("patientId")
                .usingColumns( "firstname", "lastname", "phonenumber", "address", "sex" );
    }

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex) {
        final Map<String, Object> args = new HashMap<>();
        args.put("firstname", firstName);
        args.put("lastname", lastName);
        args.put("phonenumber", phoneNumber);
        args.put("address", address);
        args.put("sex", sex);
        final Number reviewId = jdbcInsert.executeAndReturnKey(args);
        return new Patient(new Integer(reviewId.intValue()), firstName, lastName, phoneNumber, address, sex);
    }
}
