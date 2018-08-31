package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
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

    @Override
    public Optional<List<Doctor>> findDoctors(Search search) {
            String select = "SELECT doctor.id, avatar, firstName, lastName, sex, address, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ";
            String from = "FROM doctor ";
            String where = generateWhere(search);
            String leftJoins = "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                    "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  " +
                    "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                    "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                    "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ";
            String groupBy = "GROUP BY doctor.id, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName";

            final List<Doctor> list = jdbcTemplate.query(select + from+ leftJoins + where + groupBy, ROW_MAPPER);

            if(list.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(list);
    }

    public String generateWhere(Search search) {
            String where = "WHERE ";

            if(search.getName() != null) {
                where+="firstName ~* '" + search.getName() + "' ";

//                if(search.getSpecialty() != null) {
//                    where+="AND specialty ~* '" + search.getSpecialty() +"' ";
//                }
//
//                if(search.getLocation() != null) {
//                    where+="AND location ~* '" + search.getLocation() +"' ";
//                }
//
//                if(search.getInsurance() != null) {
//                    where+="AND insurance ~* '" + search.getInsurance() +"' ";
//                }

            }

            else if(search.getSpecialty() != null) {
                where+="specialty=";

                if(search.getLocation() != null) {
                    where+="AND location=";
                }

                if(search.getInsurance() != null) {
                    where+="AND insurance=";
                }

            }

            else if(search.getLocation() != null) {
                where+="location=";

                if(search.getInsurance() != null) {
                    where+="AND insurance=";
                }

            }

        return where;
    }

};




