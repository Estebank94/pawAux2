package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

    @Repository
    public class DoctorDaoImpl implements DoctorDao {

        private final JdbcTemplate jdbcTemplate;
        private SimpleJdbcInsert jdbcInsert;

        @Autowired
        public DoctorDaoImpl(final DataSource ds){
            jdbcTemplate = new JdbcTemplate(ds);
            jdbcInsert = new SimpleJdbcInsert(ds)
                    .withTableName("doctor")
                    .usingColumns("firstname","lastname","sex","phonenumber",
                            "address","licence","avatar","district")
                    .usingGeneratedKeyColumns("id");
        }

        @Override
        public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                                   String avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException {
            final Map<String,Object> entry = new HashMap<>();

            entry.put("firstname",firstName);
            entry.put("lastname",lastName);
            entry.put("phoneNumber",phoneNumber);
            entry.put("sex",sex);
            entry.put("licence",licence);
            entry.put("avatar",avatar);
            entry.put("address",address);
            Doctor doctor = null;
            Number doctorId = null;
            try{
                doctorId = jdbcInsert.executeAndReturnKey(entry);
                doctor = new Doctor(firstName,lastName,sex,address,avatar, doctorId.intValue(),phoneNumber);

            }catch (DuplicateKeyException ex1){
                throw new RepeatedLicenceException();
            }
            if (doctor == null || doctorId == null){
                throw new NotCreateDoctorException();
            }
            return doctor;
        }

       @Override
        public Optional<CompressedSearch> listDoctors() {
            StringBuilder query = new StringBuilder();
            query.append("SELECT doctor.id, avatar, firstName, lastName, sex, address, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName,information.languages, information.certificate, information.education, phoneNumber,dayweek, starttime, finishtime ")
                    .append("FROM doctor ")
                    .append("LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID ")
                    .append("LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  ")
                    .append("LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id ")
                    .append("LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID ")
                    .append("LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ")
                    .append("LEFT JOIN information ON doctor.id = information.doctorid ")
                    .append("LEFT JOIN workinghour ON doctor.id = workinghour.doctorid")
                    .append("AND specialtyname IS NOT NULL ")
                    .append("AND insuranceName IS NOT NULL");


           final CompressedSearch compressedSearch = jdbcTemplate.query(query.toString(), new CompressedExtractor());

            if(compressedSearch.getDoctors().isEmpty()){
                return Optional.empty();
            }

            return Optional.of(compressedSearch);
        }

        @Override
        public Optional<CompressedSearch> findDoctors(Search search) {

            StringBuilder select = new StringBuilder();
            select.append("SELECT doctor.id, avatar, firstName, lastName, sex, address, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName ,information.languages, information.certificate, information.education, phoneNumber,dayweek, starttime, finishtime ");
            select.append("FROM doctor ");


            Optional<String> name = search.getName().equals("")? Optional.ofNullable(null):Optional.ofNullable(search.getName());
            Optional<String> specialty = search.getSpecialty().equals("noSpecialty")?Optional.ofNullable(null):Optional.ofNullable(search.getSpecialty());
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
            } else {
                insurancePlan = Optional.ofNullable(null);
            }

            select.append("LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID ")
                    .append("LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  ")
                    .append("LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id ")
                    .append("LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID ")
                    .append("LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ")
                    .append("LEFT JOIN information ON doctor.id = information.doctorId ")
                    .append("LEFT JOIN workinghour ON doctor.id = workinghour.doctorid ");


            select.append("WHERE doctor.id IN (SELECT doctor.id " + " FROM doctor ");
            select.append("LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID ");
            select.append("LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id ");
            select.append("LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id ");
            select.append("LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID ");
            select.append("LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ");

            boolean whereInStarts = false;
            List<String> parameters = new ArrayList<>();

            if (name.isPresent())
            {
                select.append("WHERE ( ( LOWER(firstName) LIKE ?  OR  LOWER(lastName) LIKE ? ) ");
                whereInStarts = true;
                parameters.add(search.getSimilarToName());
                parameters.add(search.getSimilarToName());

            }

            if (specialty.isPresent())
            {
                if (whereInStarts)
                {
                    select.append(" AND specialty.specialtyName = ? ");
                } else {
                    select.append("WHERE(specialty.specialtyName = ? ");
                    whereInStarts = true;
                }
                parameters.add(search.getSpecialty());
            }

            if (insurance.isPresent())
            {
                if (whereInStarts)
                {
                    select.append(" AND insurance.insuranceName = ? ");
                } else  {
                    select.append(" WHERE(insurance.insuranceName = ? ");
                    whereInStarts = true;
                }
                parameters.add(search.getInsurance());

                if (insurancePlan.isPresent()) {
                    select.append("AND insurancePlan.insurancePlanName IN ");
                    select.append(search.getInsurancePlanAsString());
                }
            }

            if (sex.isPresent()){
                if (whereInStarts)
                {
                    select.append(" AND sex = ? ");
                } else  {
                    select.append(" WHERE(sex = ? ");
                    whereInStarts = true;
                }
                parameters.add(search.getSex());

            }

            if (whereInStarts == true)
            {
                select.append(")");
            }
            select.append(") ");
            select.append("AND specialtyName IS NOT NULL ");
            select.append("AND insuranceName IS NOT NULL");


            final CompressedSearch compressedSearch = jdbcTemplate.query(select.toString() , new CompressedExtractor(),
                    parameters.toArray());

            if(compressedSearch.getDoctors().isEmpty()){
                return Optional.empty();
            }

            return Optional.of(compressedSearch);
        }

        @Override
        public Optional<Doctor> findDoctorById(Integer id){

            StringBuilder select = new StringBuilder();
            select.append("SELECT doctor.id, avatar, firstName, lastName, sex, address, specialty.specialtyName, insurance.insuranceName, insurancePlan.insurancePlanName, information.languages, information.certificate, information.education, phoneNumber,dayweek, starttime, finishtime ");
            select.append("FROM doctor ");
            select.append("LEFT JOIN medicalCare ON doctor.id = medicalCare.doctorID ");
            select.append("LEFT JOIN insurancePlan ON medicalCare.insurancePlanID = insurancePlan.id  ");
            select.append("LEFT JOIN insurance ON insurancePlan.insuranceid = insurance.id ");
            select.append("LEFT JOIN doctorSpecialty ON doctor.id = doctorSpecialty.doctorID ");
            select.append("LEFT JOIN specialty ON specialty.id = doctorSpecialty.specialtyID ");
            select.append("LEFT JOIN information ON doctor.id = information.doctorId ");
            select.append("LEFT JOIN workinghour ON doctor.id = workinghour.doctorid " );
            select.append("WHERE doctor.id = ?");

            final CompressedSearch compressedSearch = jdbcTemplate.query(select.toString() , new CompressedExtractor(), id);

            if(compressedSearch.getDoctors().isEmpty()){
                return Optional.empty();
            }
            compressedSearch.getDoctors().get(0).setAppointments(findDoctorAppointmentsById(id));
            compressedSearch.getDoctors().get(0).setReviews(findDoctorReviewsById(id));
            return Optional.of(compressedSearch.getDoctors().get(0));
        }

        private static final RowMapper<Appointment> ROW_MAPPER_APPOINTMENT = (rs, rowNum) -> new Appointment(
                LocalDate.parse(rs.getString("appointmentday")) ,
                LocalTime.parse(rs.getString("appointmenttime")),
                Integer.valueOf(rs.getInt("clientid")),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("phoneNumber"));


        private Set<Appointment> findDoctorAppointmentsById(Integer id){
            Set<Appointment> appointments = new HashSet<>();
            StringBuilder query = new StringBuilder();
            query.append("SELECT appointment.doctorId, appointment.clientId, appointmentDay, appointmentTime, patient.firstname , patient.lastname, patient.phoneNumber ")
                    .append("FROM doctor ")
                    .append("JOIN appointment ON doctor.id = appointment.doctorId ")
                    .append("JOIN patient ON appointment.clientid = patient.id ")
                    .append("WHERE doctor.id = ?");

            List<Appointment> appointmentsList = jdbcTemplate.query(query.toString(), ROW_MAPPER_APPOINTMENT,id);
            for (Appointment appointmentIterator: appointmentsList){
                appointments.add(appointmentIterator);
            }
            return appointments;
        }

        private static final RowMapper<Review> ROW_MAPPER_REVIEWS = (rs, rowNum) -> new Review(
                rs.getInt("stars")
                ,LocalDateTime.parse(rs.getString("dateTime"))
                ,rs.getString("description")
                ,new Integer (rs.getInt("id"))
                ,rs.getString("patient.firstname")
                ,rs.getString("patient.lastname"));

        private List<Review> findDoctorReviewsById(Integer id){
            List<Review> reviews = new ArrayList<>();
            StringBuilder query = new StringBuilder();
            query.append("SELECT doctor.Id, stars, daytime, review.id, description, patient.firstname , patient.lastname ")
                    .append("FROM doctor ")
                    .append("JOIN review ON doctor.id = review.doctorId ")
                    .append("JOIN patient ON userId = patient.id ")
                    .append("WHERE doctor.id = ?");

            reviews = jdbcTemplate.query(query.toString(), ROW_MAPPER_REVIEWS,id);

            return reviews;
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

        private final class CompressedExtractor implements ResultSetExtractor<CompressedSearch> {
            @Override
            public CompressedSearch extractData(ResultSet rs) throws SQLException {
                CompressedSearch compressedSearch = new CompressedSearch();
                int i = 0;
                while (rs.next()) {
                    i++;
                    boolean containsDoctor = false;
                    boolean containsInsurance = false;
                    for(Doctor existingDoctor : compressedSearch.getDoctors()){
                        if (existingDoctor.getId().equals(rs.getInt("id"))) {
                            containsDoctor = true;
                            existingDoctor.getSpecialty().add(rs.getString("specialtyName"));
//                            existingDoctor.getDescription().getCertificate().add(rs.getString("certificate"));
                            existingDoctor.getDescription().getLanguages().add(rs.getString("languages"));
//                            existingDoctor.getDescription().getEducation().add(rs.getString("education"));
                            existingDoctor.getInsurance().keySet().remove(null);
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

                            Map<DayOfWeek,List<WorkingHours>> whmap = existingDoctor.getWorkingHours();
                            if (rs.getInt("dayweek")!= 0) {
                                DayOfWeek dayOfWeek = DayOfWeek.of(rs.getInt("dayweek"));
                                LocalTime st = LocalTime.parse(rs.getString("starttime"));
                                LocalTime ft = LocalTime.parse(rs.getString("finishtime"));
                                WorkingHours wh = new WorkingHours(dayOfWeek, st, ft);
                                if (!whmap.containsKey(dayOfWeek)) {
                                    List<WorkingHours> workingHours = new ArrayList<>();
                                    workingHours.add(wh);
                                    existingDoctor.getWorkingHours().put(dayOfWeek, workingHours);
                                } else if (!whmap.get(dayOfWeek).contains(wh)) {
                                    whmap.get(dayOfWeek).add(wh);
                                }
                            }
                        }
                    }
                    if(!containsDoctor) {

                        Set<String> specialty = new HashSet<>();

                        specialty.add(rs.getString("specialtyName"));

                        Set<String> insurancePlanSet = new HashSet<>();
                        insurancePlanSet.add(rs.getString("insurancePlanName"));

                        Set<String> languages = new HashSet<>();
                        languages.add(rs.getString("languages"));
                        Description description = new Description(rs.getString("certificate"), languages, rs.getString("education"));

                        Map<String, Set<String>> insurancePlan = new HashMap<>();
                        insurancePlan.put(rs.getString("insuranceName"), insurancePlanSet);

                        Map<DayOfWeek, List<WorkingHours>> workingHours = new HashMap<>();
                        if (rs.getInt("dayweek")!= 0) {
                            DayOfWeek dayOfWeek = DayOfWeek.of(rs.getInt("dayweek"));
                            LocalTime st = LocalTime.parse(rs.getString("starttime"));
                            LocalTime ft = LocalTime.parse(rs.getString("finishtime"));
                            WorkingHours wh = new WorkingHours(dayOfWeek, st, ft);
                            List<WorkingHours> workingHoursList = new ArrayList<>();
                            workingHoursList.add(wh);
                            workingHours.put(dayOfWeek, workingHoursList);
                        }

                        Doctor doctor = new Doctor(rs.getString("firstName"), rs.getString("lastName"), rs.getString("sex"),
                                rs.getString("address"), rs.getString("avatar"), specialty, insurancePlan, rs.getInt("id"), description, rs.getString("phoneNumber"),workingHours);

                        compressedSearch.getDoctors().add(doctor);
                        compressedSearch.getSex().add(rs.getString("sex"));
                        compressedSearch.getInsurance().put(rs.getString("insuranceName"), insurancePlanSet);
                    }

                }

                return compressedSearch;
            }
        }


        private static final RowMapper<Integer> ROW_MAPPER_IS_EXISTING_LICENCE = (rs, rowNum) -> new Integer(rs.getInt("id"));

        @Override
        public Boolean isAnExistingLicence(Integer licence){
            StringBuilder query = new StringBuilder();
            query.append("SELECT id ")
                    .append("FROM doctor ")
                    .append("WHERE licence = ?");
            List<Integer> id = jdbcTemplate.query(query.toString(),ROW_MAPPER_IS_EXISTING_LICENCE,licence);
            if (id == null || id.size() == 0){
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    };