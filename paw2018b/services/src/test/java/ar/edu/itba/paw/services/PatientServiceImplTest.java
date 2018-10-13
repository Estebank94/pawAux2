//package ar.edu.itba.paw.services;
//
//import ar.edu.itba.paw.models.Patient;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import javax.sql.DataSource;
//
//import static org.junit.Assert.*;
//
//@Sql("classpath:ServiceTest.sql")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class PatientServiceImplTest {
//
//    private static final String PATIENT_EMAIL = "Roberto@rosa.com";
//    private static final Integer PATIENT_ID = 5;
//    private static final Integer PATIENT_DOC_ID = 1;
//
//    private static final String NEW_PATIENT_NAME = "Mike";
//    private static final String NEW_PATIENT_LASTNAME = "Muchpain";
//    private static final String NEW_PATIENT_PHONE = "1512341234";
//    private static final String NEW_PATIENT_EMAIL = "mike@lovewaldoc.com";
//    private static final String NEW_PATIENT_PASSWORD = "SecretPass";
//
//    private static final Integer PATIENTS_QUANTITY_BEFORE = 2;
//
//    @Autowired
//    private PatientServiceImpl patientServiceImpl;
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
//    }
//
//    @After
//    public void tearDown(){
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "workingHour", "medicalCare", "doctorSpecialty", "doctor", "insurancePlan", "insurance",
//                "Specialty", "review", "information", "appointment", "patient");
//    }
//
//    @Test
//    public void testCreatePatient() throws Exception {
//
//        Patient newPatient = patientServiceImpl.createPatient(NEW_PATIENT_NAME, NEW_PATIENT_LASTNAME, NEW_PATIENT_PHONE, NEW_PATIENT_EMAIL, NEW_PATIENT_PASSWORD);
//
//        assertNotNull(newPatient);
//        assertEquals(NEW_PATIENT_NAME, newPatient.getFirstName());
//        assertEquals(NEW_PATIENT_LASTNAME, newPatient.getLastName());
//        assertEquals(NEW_PATIENT_PHONE, newPatient.getPhoneNumber());
//        assertEquals(NEW_PATIENT_EMAIL, newPatient.getEmail());
//        assertEquals(NEW_PATIENT_PASSWORD, newPatient.getPassword());
//        assertEquals(PATIENTS_QUANTITY_BEFORE + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "patient"));
//
//    }
//
//    @Test
//    public void testFindByEmail() throws Exception{
//
//        Patient patientByEmail = patientServiceImpl.findPatientByEmail(PATIENT_EMAIL);
//
//        assertNotNull(patientByEmail);
//        assertEquals(PATIENT_ID, patientByEmail.getPatientId());
//        assertEquals(PATIENT_EMAIL, patientByEmail.getEmail());
//
//    }
//
//    @Test
//    public void testFindById() throws Exception{
//
//        Patient patientById = patientServiceImpl.findPatientById(PATIENT_ID);
//
//        assertNotNull(patientById);
//        assertEquals(PATIENT_ID, patientById.getPatientId());
//
//    }
//
//    @Test
//    public void testSetPatientInfo() throws Exception {
//
//        Boolean setInfo = patientServiceImpl.setDoctorId(PATIENT_ID, PATIENT_DOC_ID);
//
//        Patient setPatient = patientServiceImpl.findPatientById(PATIENT_ID);
//
//        assertTrue(setInfo);
//        assertEquals(PATIENT_DOC_ID, setPatient.getDoctorId());
//        assertEquals(PATIENT_ID, setPatient.getPatientId());
//
//    }
//
//
//
//}
