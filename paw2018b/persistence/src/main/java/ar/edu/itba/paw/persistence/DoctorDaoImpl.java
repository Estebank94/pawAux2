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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorDaoImpl implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Doctor> ROW_MAPPER = new RowMapper<Doctor>(){

        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
                    rs.getString("address"), rs.getString("avatar"), rs.getString("specialtyName"), rs.getString("workingHours"), rs.getInt("id"));}
        };

        @Autowired
        public DoctorDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        }

    @Override
    public Optional<List<Doctor>> listDoctors() {
        String select = "SELECT doctor.id, avatar, firstName, lastName, sex, address, workingHours, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ";
        String from = "FROM doctor ";
        String leftJoins = "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  " +
                "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ";
        String groupBy = "GROUP BY doctor.id, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName";

        final List<Doctor> list = jdbcTemplate.query(select + from + leftJoins + groupBy, ROW_MAPPER);

        if(list.isEmpty()){
            return Optional.empty();
        }
            return Optional.of(list);
    }

    @Override
    public Optional<List<Doctor>> findDoctors(Search search) {

            String select = "SELECT doctor.id, avatar, firstName, lastName, sex, address, workingHours, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ";
            String from = "FROM doctor ";
            String where;
            if(search.getName().isEmpty() && search.getSpecialty().isEmpty() && search.getInsurance().matches("no")){
                where = " ";
            }else{
                where = generateWhere(search);
            }

            String leftJoins = "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                    "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  " +
                    "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                    "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                    "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ";
            String groupBy = "GROUP BY doctor.id, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName";

           final List<Doctor> list = jdbcTemplate.query(select + from + leftJoins + where + groupBy, ROW_MAPPER);

            if(list.isEmpty()){
                //TODO
                //Vamos a tener que reveer esto porque no siempre me tiene que dar
                //todos si por ejemplo me dice che ... quiero cardiologo pero no tengo de esa obra social
                //listar cardiologos aunque no sean de su obra social, su urgencia es el cardiologo !!
                return Optional.empty();
            }

            List<Integer> rta = new ArrayList<>();
            List<Doctor> dct = new ArrayList<>();

            for(Doctor doctor : list){
                if(!rta.contains(doctor.getId())){
                    rta.add(doctor.getId());
                    dct.add(doctor);
                }
                for(Doctor doctorIt : dct){
                    //TODO
                    //Cuando veo que tengo el ID del doctor en mi lista de ya vistos, tendria que encontrar
                    //la forma de que el doctor ahora tenga una lista, y en esa lista, le pongo las insurances y los planes
                    //de las mismas + tener en cuenta un mapa, un algo de eso.
                }
            }
            return Optional.of(dct);
    }

    public String generateWhere(Search search) {
            String where = "WHERE (";


            if(!search.getName().isEmpty()) {
                where+="(firstName ~* '" + search.getName() + "' OR lastName ~* '" + search.getName() + "') " ;

                if(!search.getSpecialty().isEmpty()) {
                    where+="AND specialty.specialtyName ~* '" + search.getSpecialty() +"' ";
                }
//
//                if(search.getLocation() != null) {
//                    where+="AND location ~* '" + search.getLocation() +"' ";
//                }
//
                if(!search.getInsurance().matches("no")) {
                    where+="AND insurance.insuranceName ~* '" + search.getInsurance() +"' ";
                }


            }


            else if(!search.getSpecialty().isEmpty()) {

                where+="specialty.specialtyName ~* '" + search.getSpecialty() + "' ";
                System.out.println("search.getSpecialty.isEmpty()" + search.getSpecialty().isEmpty());

//                if(!search.getLocation().isEmpty()) {
//                    where+="AND location=";
//                }

                if(!search.getInsurance().matches("no")) {
                    where+="AND insurance.insuranceName= '" + search.getInsurance() + "' ";
                }

            }

            else if(!search.getInsurance().matches("no")) {
                where+="insurance.insuranceName= '" + search.getInsurance() + "' " ;

            }

            where += ") ";
        return where;
    }

};




