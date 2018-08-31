package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorDaoImpl implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Doctor> ROW_MAPPER = new RowMapper<Doctor>(){

        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
                    rs.getString("address"), rs.getString("avatar"), rs.getInt("id"));}
        };

        @Autowired
        public DoctorDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        }

    @Override
    public Optional<List<Doctor>> listDoctors() {
            final List<Doctor> list = jdbcTemplate.query("SELECT * FROM doctor", ROW_MAPPER);

            if(list.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(list);
    }
};




