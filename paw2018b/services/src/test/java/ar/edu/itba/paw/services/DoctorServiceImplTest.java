//package ar.edu.itba.paw.services;
//
//import ar.edu.itba.paw.models.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import javax.sql.DataSource;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.*;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//
//@Sql("classpath:ServiceTest.sql")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class DoctorServiceImplTest {
//
//    @Autowired
//    private DoctorServiceImpl doctorServiceImpl;
//
//    private Doctor doctor, doctor2, doctor3;
//    private WorkingHours workingHours;
//    private Description description;
//    private Search search;
//
//    private static final String DOC_NAME = "Roberto Nicolas Agustin";
//    private static final String DOC_SECOND_NAME = "Nicolas";
//    private static final Integer DOC_ID = 1;
//    private static final String DOC_SPECIALTY = "NUTRICIÓN";
//    private static final String DOC_INSURANCE = "Accord";
//    private static final String DOC_INSURANCE_PLAN_AS_STRING = "('Accord Salud')";
//    private static final String DOC_INSURANCE_PLAN = "Accord Salud";
//    private static final String DOC_SEX = "M";
//
//    private static final String NEW_DOC_NAME = "Marlon Jay";
//    private static final String NEW_DOC_LASTNAME = "Brando";
//    private static final String NEW_DOC_PHONE = "47771234";
//    private static final String NEW_DOC_SEX = "M";
//    private static final String NEW_DOC_LICENSE = "2020";
//    private static final String NEW_DOC_AVATAR = "https://d1cesmq0xhh7we.cloudfront.net/cb5ddc05-1d68-48ca-a8ff-baba8239be85circle_medium__v1__.png";
//    private static final String NEW_DOC_ADDRESS = "Cabildo 650";
//
//    private static final int DOCTOR_QUANTITY_BEFORE = 3;
//
//    private static final DayOfWeek DAY_OF_WEEK = DayOfWeek.FRIDAY;
//    private static final LocalTime START = LocalTime.of(9, 10, 50);
//    private static final LocalTime END = LocalTime.of(11, 45, 20);
//    private static final String CERTIFICATE = "BACHELOR";
//    private static final String EDUCATION = "ITBA";
//
//    @Autowired
//    private DataSource ds;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp() {
//
//        jdbcTemplate = new JdbcTemplate(ds);
//
//        doctor = Mockito.mock(Doctor.class);
//        doctor2 = Mockito.mock(Doctor.class);
//        doctor3 = Mockito.mock(Doctor.class);
//        search = Mockito.mock(Search.class);
//        when(doctor.getId()).thenReturn(1);
//        when(doctor2.getId()).thenReturn(2);
//        when(doctor3.getId()).thenReturn(3);
//
//    }
//
//    @After
//    public void tearDown(){
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "workingHour", "medicalCare", "doctorSpecialty", "doctor", "insurancePlan", "insurance",
//                "Specialty", "review", "information", "appointment", "patient");
//    }
//
//    @Test
//    public void testList() {
//
//        final Optional<CompressedSearch> doctors = doctorServiceImpl.listDoctors();
//
//        assertNotNull(doctors.get());
//        assertEquals(DOCTOR_QUANTITY_BEFORE, doctors.get().getDoctors().size());
//        assertEquals(doctor.getId(), doctors.get().getDoctors().get(0).getId());
//        assertEquals(doctor2.getId(), doctors.get().getDoctors().get(1).getId());
//        assertEquals(doctor3.getId(), doctors.get().getDoctors().get(2).getId());
//
//    }
//
//    @Test
//    public void testFind() throws Exception{
//
//        StringBuilder nameBuilder = new StringBuilder();
//        nameBuilder.append("%");
//        nameBuilder.append(DOC_SECOND_NAME.toLowerCase());
//        nameBuilder.append("%");
//
//        List<String> insurancePlan = new ArrayList<>();
//        insurancePlan.add(DOC_INSURANCE_PLAN);
//
//        when(search.getName()).thenReturn(DOC_SECOND_NAME);
//        when(search.getSimilarToName()).thenReturn(nameBuilder.toString());
//        when(search.getSpecialty()).thenReturn(DOC_SPECIALTY);
//        when(search.getInsurance()).thenReturn(DOC_INSURANCE);
//        when(search.getInsurancePlanAsString()).thenReturn(DOC_INSURANCE_PLAN_AS_STRING);
//        when(search.getSex()).thenReturn(DOC_SEX);
//
//        Optional<CompressedSearch> filteredSearch = filteredSearch = doctorServiceImpl.findDoctors(search);
//
//        assertTrue(filteredSearch.isPresent());
//        assertEquals(1, filteredSearch.get().getDoctors().size() );
//        assertEquals( DOC_NAME, filteredSearch.get().getDoctors().get(0).getFirstName() );
//        assertEquals( DOC_ID, filteredSearch.get().getDoctors().get(0).getId() );
//        assertTrue( filteredSearch.get().getDoctors().get(0).getSpecialty().contains(DOC_SPECIALTY) );
//        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().containsKey(DOC_INSURANCE) );
//        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().get(DOC_INSURANCE).contains(DOC_INSURANCE_PLAN) );
//        assertEquals( DOC_SEX, filteredSearch.get().getDoctors().get(0).getSex() );
//
//    }
//
//    @Test
//    public void testFindById() throws Exception{
//
//        Optional<Doctor> filteredById = filteredById = doctorServiceImpl.findDoctorById(DOC_ID);
//
//        assertTrue(filteredById.isPresent());
//       assertEquals(doctor.getId(), filteredById.get().getId());
//
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//
//        Doctor newDoctor = doctorServiceImpl.createDoctor(NEW_DOC_NAME, NEW_DOC_LASTNAME, NEW_DOC_PHONE, NEW_DOC_SEX, NEW_DOC_LICENSE, NEW_DOC_ADDRESS, NEW_DOC_AVATAR);
//
//        assertNotNull(newDoctor);
//        assertEquals(NEW_DOC_NAME, newDoctor.getFirstName());
//        assertEquals(NEW_DOC_LASTNAME, newDoctor.getLastName());
//        assertEquals(NEW_DOC_PHONE, newDoctor.getPhoneNumber());
//        assertEquals(NEW_DOC_SEX, newDoctor.getSex());
//        assertEquals(NEW_DOC_AVATAR, newDoctor.getAvatar());
//        assertEquals(NEW_DOC_ADDRESS, newDoctor.getAddress());
//        assertEquals(DOCTOR_QUANTITY_BEFORE + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "doctor"));
//
//    }
//
//    @Test
//    public void testSetDoctorInfo() throws Exception{
//
//        workingHours = Mockito.mock(WorkingHours.class);
//        description = Mockito.mock(Description.class);
//
//        when(workingHours.getStartTime()).thenReturn(START);
//        when(workingHours.getFinishTime()).thenReturn(END);
//        when(workingHours.getDayOfWeek()).thenReturn(DAY_OF_WEEK);
//        when(description.getCertificate()).thenReturn(CERTIFICATE);
//        when(description.getEducation()).thenReturn(EDUCATION);
//
//        Set<String> specialtySet = new HashSet<>();
//        specialtySet.add(DOC_SPECIALTY);
//        Set<String> insurancePlanSet = new HashSet<>();
//        insurancePlanSet.add(DOC_INSURANCE_PLAN);
//        Map<String, Set<String>> insuranceMap = new HashMap<>();
//        insuranceMap.put(DOC_INSURANCE, insurancePlanSet);
//        List<WorkingHours> workingHoursList = new ArrayList<>();
//        workingHoursList.add(workingHours);
//        Map<DayOfWeek, List<WorkingHours>> workingHoursMap = new HashMap<>();
//        workingHoursMap.put(DAY_OF_WEEK, workingHoursList);
//
//        final Optional<Doctor> setDoctor = doctorServiceImpl.setDoctorInfo(DOC_ID, specialtySet, insuranceMap, workingHoursList, description);
//
//        assertTrue(setDoctor.isPresent());
//        assertEquals(DOC_ID, setDoctor.get().getId());
//        assertTrue(setDoctor.get().getInsurance().containsKey(DOC_INSURANCE));
//        assertTrue(setDoctor.get().getInsurance().containsValue(insurancePlanSet));
//        assertTrue(setDoctor.get().containsSpecialty(specialtySet));
//        assertEquals(description, setDoctor.get().getDescription());
//
//    }
//
//    @Test
//    public void testSetDoctorInsurance() {
//
//        Set<String> insurancePlanSet = new HashSet<>();
//        insurancePlanSet.add(DOC_INSURANCE_PLAN);
//        Map<String, Set<String>> insuranceMap = new HashMap<>();
//        insuranceMap.put(DOC_INSURANCE, insurancePlanSet);
//
//        final Optional<Doctor> setDoctorInsurance = doctorServiceImpl.setDoctorInsurance(DOC_ID, insuranceMap);
//
//        assertTrue(setDoctorInsurance.isPresent());
//        assertTrue(setDoctorInsurance.get().getInsurance().containsKey(DOC_INSURANCE));
//        assertTrue(setDoctorInsurance.get().getInsurance().containsValue(insurancePlanSet));
//
//    }
//
//    @Test
//    public void testSetDoctorSpecialty() {
//
//        Set<String> specialtySet = new HashSet<>();
//        specialtySet.add(DOC_SPECIALTY);
//
//        final Optional<Doctor> setDoctorSpecialty = doctorServiceImpl.setDoctorSpecialty(DOC_ID, specialtySet);
//
//        assertTrue(setDoctorSpecialty.isPresent());
//        assertTrue(setDoctorSpecialty.get().containsSpecialty(specialtySet));
//
//    }
//
//    @Test
//    public void testSetWorkingHours() {
//
//        workingHours = Mockito.mock(WorkingHours.class);
//        when(workingHours.getStartTime()).thenReturn(START);
//        when(workingHours.getFinishTime()).thenReturn(END);
//        when(workingHours.getDayOfWeek()).thenReturn(DAY_OF_WEEK);
//
//        List<WorkingHours> workingHoursList = new ArrayList<>();
//        workingHoursList.add(workingHours);
//        Map<DayOfWeek, List<WorkingHours>> workingHoursMap = new HashMap<>();
//        workingHoursMap.put(DAY_OF_WEEK, workingHoursList);
//
//        final Optional<Doctor> setDoctorWorkingHours = doctorServiceImpl.setWorkingHours(DOC_ID, workingHoursList);
//
//        assertTrue(setDoctorWorkingHours.isPresent());
//        assertTrue(setDoctorWorkingHours.get().getWorkingHours().containsKey(DAY_OF_WEEK));
//        assertEquals(DAY_OF_WEEK, setDoctorWorkingHours.get().getWorkingHours().get(DAY_OF_WEEK).get(0).getDayOfWeek());
//        assertEquals(START, setDoctorWorkingHours.get().getWorkingHours().get(DAY_OF_WEEK).get(0).getStartTime());
//        assertEquals(END, setDoctorWorkingHours.get().getWorkingHours().get(DAY_OF_WEEK).get(0).getFinishTime());
//
//    }
//
//}