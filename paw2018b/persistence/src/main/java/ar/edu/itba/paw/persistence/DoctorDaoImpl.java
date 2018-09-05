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
import java.util.*;

@Repository
public class DoctorDaoImpl implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Doctor> ROW_MAPPER = new RowMapper<Doctor>(){

        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {

            Set<String> specialty = new HashSet<>();

            specialty.add(rs.getString("specialtyName"));

            Set<String> insurancePlanSet = new HashSet<>();
            insurancePlanSet.add(rs.getString("insurancePlanName"));

            Map<String, Set<String>> insurancePlan = new HashMap<>();
            insurancePlan.put(rs.getString("insuranceName"),insurancePlanSet);


            return new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
                    rs.getString("address"), rs.getString("avatar"), specialty, insurancePlan, rs.getString("workingHours"), rs.getInt("id"));}
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

            List<Doctor> ans = compressDoctors(list);

            return Optional.of(ans);
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

    private List<Doctor> compressDoctors(List<Doctor> list){
        List<Doctor> ans = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            boolean flag = true;

            for (int j = 0; j < ans.size() && flag; j++) {
                Doctor lstDoc = list.get(i);
                Doctor ansDoc = ans.get(j);
                if (ansDoc.equals(lstDoc)) {
                    flag = false;
                    ansDoc.getSpecialty().addAll(lstDoc.getSpecialty());

                    for(String lstKey : lstDoc.getInsurance().keySet()) {
                        if (!ansDoc.getInsurance().containsKey(lstKey)) {
                            ansDoc.getInsurance().put(lstKey,lstDoc.getInsurance().get(lstKey));
                        } else {
                            ansDoc.getInsurance().get(lstKey).addAll(lstDoc.getInsurance().get(lstKey));
                        }
                    }

                }


            }

            if (flag == true) {
                ans.add(list.get(i));
            }
        }
        return ans;

    }
};




