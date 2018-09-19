package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

    @Repository
    public class DoctorDaoImpl implements DoctorDao {

        private final JdbcTemplate jdbcTemplate;

//            private static final RowMapper<Doctor> ROW_MAPPER = new RowMapper<Doctor>(){
//
//                @Override
//                public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//                    Set<String> specialty = new HashSet<>();
//
//                    specialty.add(rs.getString("specialtyName"));
//
//                    Set<String> insurancePlanSet = new HashSet<>();
//                    insurancePlanSet.add(rs.getString("insurancePlanName"));
//
//                    Map<String, Set<String>> insurancePlan = new HashMap<>();
//                    insurancePlan.put(rs.getString("insuranceName"),insurancePlanSet);
//
//
//                    return new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
//                            rs.getString("address"), rs.getString("avatar"), specialty, insurancePlan, rs.getString("workingHours"), rs.getInt("id"));}
//            };

//            private static final ResultSetExtractor<CompressedSearch> RESULT_SET = (rs) -> {
//                        CompressedSearch compressedSearch = new CompressedSearch();
//
//                        while (rs.next()) {
//                            System.out.println(rs.getString("firstName") + rs.getString("lastName"));
//                            boolean containsDoctor = false;
//                            boolean containsInsurance = false;
//                            for(Doctor existingDoctor : compressedSearch.getDoctors()){
//                                if (existingDoctor.getId().equals(rs.getInt("id"))) {
//                                    containsDoctor = true;
//                                    existingDoctor.getSpecialty().add(rs.getString("specialtyName"));
//
//                                    for(String insurance : existingDoctor.getInsurance().keySet()){
//                                        if(insurance.equals(rs.getString("insuranceName"))){
//                                            containsInsurance = true;
//                                            existingDoctor.getInsurance().get(insurance).add(rs.getString("insurancePlanName"));
//                                            compressedSearch.getInsurance().get(insurance).add(rs.getString("insurancePlanName"));
//                                        }
//                                    }
//                                    if(!containsInsurance){
//                                        Set<String> insurancePlans = new HashSet<>();
//                                        insurancePlans.add(rs.getString("insurancePlanName"));
//                                        existingDoctor.getInsurance().put(rs.getString("insuranceName"), insurancePlans);
//                                        compressedSearch.getInsurance().put(rs.getString("insuranceName"), insurancePlans);
//                                    }
//                                }
//                            }
//                            if(!containsDoctor){
//                                Set<String> specialty = new HashSet<>();
//
//                                specialty.add(rs.getString("specialtyName"));
//
//                                Set<String> insurancePlanSet = new HashSet<>();
//                                insurancePlanSet.add(rs.getString("insurancePlanName"));
//
//                                Map<String, Set<String>> insurancePlan = new HashMap<>();
//                                insurancePlan.put(rs.getString("insuranceName"),insurancePlanSet);
//
//                                Doctor doctor =  new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
//                                        rs.getString("address"), rs.getString("avatar"), specialty, insurancePlan, rs.getString("workingHours"), rs.getInt("id"));
//
//                                compressedSearch.getDoctors().add(doctor);
//                                compressedSearch.getSex().add(rs.getString("sex"));
//                                compressedSearch.getInsurance().put(rs.getString("insuranceName"), insurancePlanSet);
//                            }
//
//
//
//                        }
//
//                        return compressedSearch;
//                }



            //    new RowMapper<Doctor>(){
//
//        @Override
//        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//            Set<String> specialty = new HashSet<>();
//
//            specialty.add(rs.getString("specialtyName"));
//
//            Set<String> insurancePlanSet = new HashSet<>();
//            insurancePlanSet.add(rs.getString("insurancePlanName"));
//
//            Map<String, Set<String>> insurancePlan = new HashMap<>();
//            insurancePlan.put(rs.getString("insuranceName"),insurancePlanSet);
//
//
//            return new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
//                    rs.getString("address"), rs.getString("avatar"), specialty, insurancePlan, rs.getString("workingHours"), rs.getInt("id"));}
//        };

        @Autowired
        public DoctorDaoImpl(final DataSource ds) {
            jdbcTemplate = new JdbcTemplate(ds);
        }

        @Override
        public Optional<CompressedSearch> listDoctors() {
            String select = "SELECT doctor.id, avatar, firstName, lastName, sex, address, workingHours, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ";
            String from = "FROM doctor ";
            String leftJoins = "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                    "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  " +
                    "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                    "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                    "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ";
            String groupBy = "GROUP BY doctor.id, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName";

            final CompressedSearch compressedSearch = jdbcTemplate.query(select + from + leftJoins + groupBy, new CompressedExtractor());

            if(compressedSearch.getDoctors().isEmpty()){
                return Optional.empty();
            }

            return Optional.of(compressedSearch);
        }

        @Override
        public Optional<CompressedSearch> findDoctors(Search search) {

            String select = "SELECT doctor.id, avatar, firstName, lastName, sex, address, workingHours, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ,information.languages, information.certificate, information.education ";
            String from = "FROM doctor ";
            String whereIn;

            Optional<String> name = search.getName().equals("")? Optional.ofNullable(null):Optional.ofNullable(search.getName());

            Optional<String> specialty = search.getSpecialty().equals("")?Optional.ofNullable(null):Optional.ofNullable(search.getSpecialty());

            Optional<String> insurance = search.getInsurance().matches("no")?Optional.ofNullable(null):Optional.ofNullable(search.getName());

            Optional<String> sex = search.getSex().equals("ALL") || search.getSex().isEmpty() || search.getSex().equals("")?Optional.ofNullable(null): Optional.ofNullable(search.getSex());

            Optional<List<String>> insurancePlan;

            if (search.getInsurancePlan() != null)
            {
                boolean hasAll = false;
                for (String insurancePlanIterator : search.getInsurancePlan()){
                    if (insurancePlanIterator.equals("ALL")) {
                        hasAll = true;
                    }
                }
                if (hasAll || search.getInsurancePlan().size() == 0)
                {
                    insurancePlan = Optional.ofNullable(null);
                } else {
                    insurancePlan =Optional.ofNullable(search.getInsurancePlan());
                }
            } else
            {
                insurancePlan = Optional.ofNullable(null);
            }
            
            String leftJoins = "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                    "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  " +
                    "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                    "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                    "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID "+
                    "LEFT JOIN information ON doctor.id = information.doctorId ";

            String whereOut = "WHERE doctor.id IN (SELECT doctor.id " + " FROM doctor " +
                    "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                    "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id " +
                    "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                    "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                    "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ";

            StringBuilder sb = new StringBuilder();
            boolean whereInStarts = false;
            List<String> parameters = new ArrayList<>();

            if (name.isPresent())
            {
                sb.append("WHERE( ( firstName ~* ? OR lastName ~* ? ) ");
                whereInStarts = true;
                parameters.add(search.getName());
                parameters.add(search.getName());

            }

            if (specialty.isPresent())
            {
                if (whereInStarts)
                {
                    sb.append(" AND specialty.specialtyName ~* ? ");
                } else {
                    sb.append("WHERE(specialty.specialtyName ~* ? ");
                    whereInStarts = true;
                }
                parameters.add(search.getSpecialty());
            }

            if (insurance.isPresent())
            {
                if (whereInStarts)
                {
                    sb.append(" AND insurance.insuranceName ~* ? ");
                } else  {
                    sb.append(" WHERE(insurance.insuranceName ~* ?");
                    whereInStarts = true;
                }

                if (insurancePlan.isPresent())
                {
                    sb.append("AND insurancePlan.insurancePlanName IN ");
                    sb.append(search.getInsurancePlanAsString());
                }
                parameters.add(search.getInsurance());

            }

            if (sex.isPresent()){
                if (whereInStarts)
                {
                    sb.append(" AND sex ~* ? ");
                } else  {
                    sb.append(" WHERE(sex ~* ?");
                    whereInStarts = true;
                }
                parameters.add(search.getSex());

            }

            if (whereInStarts == true)
            {
                sb.append(")");
            }
            sb.append(")");
            whereIn = sb.toString();

            final CompressedSearch compressedSearch = jdbcTemplate.query(select + from + leftJoins + whereOut + whereIn, new CompressedExtractor(),
                    parameters.toArray());

            if(compressedSearch.getDoctors().isEmpty()){
                return Optional.empty();
            }

            return Optional.of(compressedSearch);
        }

        @Override
        public Optional<Doctor> findDoctorById(Integer id){
            String select = "SELECT doctor.id, avatar, firstName, lastName, sex, address, workingHours, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ";
            String from = "FROM doctor ";
            String leftJoins = "LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID " +
                    "LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  " +
                    "LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id " +
                    "LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID " +
                    "LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID "+
                    "LEFT JOIN information ON doctor.id = information.doctorId ";
            String where = "WHERE doctor.id = ?";
            final CompressedSearch compressedSearch = jdbcTemplate.query(select + from + leftJoins + where, new CompressedExtractor(), id);

            if(compressedSearch.getDoctors().isEmpty()){
                return Optional.empty();
            }

            return Optional.of(compressedSearch.getDoctors().get(0));
        }

//    public String generateWhere(Search search) {
//            String where = "WHERE (";
//
//
//            if(!search.getName().isEmpty()) {
//                where+="(firstName ~* '" + search.getName() + "' OR lastName ~* '" + search.getName() + "') " ;
//
//                if(!search.getSpecialty().isEmpty()) {
//                    where+="AND specialty.specialtyName ~* '" + search.getSpecialty() +"' ";
//                }
////
////                if(search.getLocation() != null) {
////                    where+="AND location ~* '" + search.getLocation() +"' ";
////                }
////
//                if(!search.getInsurance().matches("no")) {
//                    where+="AND insurance.insuranceName ~* '" + search.getInsurance() +"' ";
//                }
//
//
//            }
//
//
//            else if(!search.getSpecialty().isEmpty()) {
//
//                where+="specialty.specialtyName ~* '" + search.getSpecialty() + "' ";
//                System.out.println("search.getSpecialty.isEmpty()" + search.getSpecialty().isEmpty());
//
////                if(!search.getLocation().isEmpty()) {
////                    where+="AND location=";
////                }
//
//                if(!search.getInsurance().matches("no")) {
//                    where+="AND insurance.insuranceName= '" + search.getInsurance() + "' ";
//                }
//
//            }
//
//            else if(!search.getInsurance().matches("no")) {
//                where+="insurance.insuranceName= '" + search.getInsurance() + "' " ;
//
//            }
//
//            where += ") ";
//        return where;
//    }

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

        private final class CompressedExtractor implements ResultSetExtractor<CompressedSearch> {
            @Override
            public CompressedSearch extractData(ResultSet rs) throws SQLException {
                CompressedSearch compressedSearch = new CompressedSearch();

                while (rs.next()) {
                    boolean containsDoctor = false;
                    boolean containsInsurance = false;
                    for(Doctor existingDoctor : compressedSearch.getDoctors()){
                        if (existingDoctor.getId().equals(rs.getInt("id"))) {
                            containsDoctor = true;
                            existingDoctor.getSpecialty().add(rs.getString("specialtyName"));

                            for(String insurance : existingDoctor.getInsurance().keySet()){
                                if(insurance.equals(rs.getString("insuranceName"))){
                                    containsInsurance = true;
                                    existingDoctor.getInsurance().get(insurance).add(rs.getString("insurancePlanName"));
                                    compressedSearch.getInsurance().get(insurance).add(rs.getString("insurancePlanName"));
                                }
                            }
                            if(!containsInsurance){
                                Set<String> insurancePlans = new HashSet<>();
                                insurancePlans.add(rs.getString("insurancePlanName"));
                                existingDoctor.getInsurance().put(rs.getString("insuranceName"), insurancePlans);
                                compressedSearch.getInsurance().put(rs.getString("insuranceName"), insurancePlans);
                            }
                        }
                    }
                    if(!containsDoctor){
                        Set<String> specialty = new HashSet<>();

                        specialty.add(rs.getString("specialtyName"));

                        Set<String> insurancePlanSet = new HashSet<>();
                        insurancePlanSet.add(rs.getString("insurancePlanName"));

                        Map<String, Set<String>> insurancePlan = new HashMap<>();
                        insurancePlan.put(rs.getString("insuranceName"),insurancePlanSet);

                        Doctor doctor =  new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
                                rs.getString("address"), rs.getString("avatar"), specialty, insurancePlan, rs.getString("workingHours"), rs.getInt("id"));

                        compressedSearch.getDoctors().add(doctor);
                        compressedSearch.getSex().add(rs.getString("sex"));
                        compressedSearch.getInsurance().put(rs.getString("insuranceName"), insurancePlanSet);
                    }



                }

                return compressedSearch;
            }
        }
    };