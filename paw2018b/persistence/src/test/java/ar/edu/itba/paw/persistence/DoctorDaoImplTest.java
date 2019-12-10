package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Description;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Specialty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@Sql("classpath:doctorDaoTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class DoctorDaoImplTest {

    private static final String NEW_DOC_NAME = "Marlon Jay";
    private static final String NEW_DOC_LASTNAME = "Brando";
    private static final String NEW_DOC_PHONE = "47771234";
    private static final String NEW_DOC_SEX = "M";
    private static final Integer NEW_DOC_LICENSE = 2020;
    private static final String NEW_DOC_AVATAR = "https://d1cesmq0xhh7we.cloudfront.net/cb5ddc05-1d68-48ca-a8ff-baba8239be85circle_medium__v1__.png";
    private static final String NEW_DOC_ADDRESS = "Cabildo 650";

    private static final String DOC_NAME = "Roberto Nicolas Agustin";
    private static final String DOC_SPECIALTY = "NUTRICIÓN";
    private static final String DOC_INSURANCE = "Accord";
    private static final String DOC_INSURANCE_PLAN = "Accord Salud";
    private static final String DOC_SEX = "M";

    private static final String DOC_SEX_FEMALE = "F";

    private static final Integer DOCTOR_QUANTITY_BEFORE = 3;
    private static final int DOCTORS_QUANTITY = 3;
    private static final int SPECIALTIES_QUANTITY = 2;
    private static final String DOC_SECOND_NAME = "Nicolas";
    private static final Integer DOCTOR_ID = 1;
    private static final Integer DOCTOR2_ID = 2;
    private static final Integer DOCTOR3_ID = 3;

    private static String CERTIFICATE = "bachiller", CERTIFICATE2 = "Master";
    private static String LANGUAGES = "chino", LANGUAGES2 = "Aleman";
    private static String EDUCATION = "jarvar", EDUCATION2 = "UBA";

    private Specialty specialty1, specialty2;
    private Doctor doctor;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DataSource ds;

    @Autowired
    private DoctorHibernateDaoImpl doctorDao;

    @Autowired
    private SpecialtyHibernateDaoImpl specialtyDao;

    @Autowired
    private DescriptionHibernateDaoImpl descriptionDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @After
    public void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "favorite", "userinfo", "verification", "workinghour",
                "review", "information", "appointment", "medicalCare", "doctorSpecialty", "doctor", "insurancePlan",
                "insurance", "Specialty", "patient");
    }

    @Test
    public void testCreate() throws Exception{
        Doctor doctor = doctorDao.createDoctor(NEW_DOC_NAME, NEW_DOC_LASTNAME, NEW_DOC_PHONE, NEW_DOC_SEX, NEW_DOC_LICENSE, null, NEW_DOC_ADDRESS);

        assertNotNull(doctor);
        assertEquals(NEW_DOC_NAME, doctor.getFirstName());
        assertEquals(NEW_DOC_LASTNAME, doctor.getLastName());
        assertEquals(NEW_DOC_PHONE, doctor.getPhoneNumber());
        assertEquals(NEW_DOC_SEX, doctor.getSex());
//        assertEquals(NEW_DOC_AVATAR, doctor.getProfilePicture());
        assertEquals(NEW_DOC_ADDRESS, doctor.getAddress());
        assertEquals(DOCTOR_QUANTITY_BEFORE + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "doctor"));
    }

    @Test
    public void testFindById() {
        Optional<Doctor> doctor = doctorDao.findDoctorById(DOCTOR_ID);

        assertTrue(doctor.isPresent());
        assertEquals(DOCTOR_ID, doctor.get().getId());
    }

    @Test
    public void testListDoctors() {
        List<Doctor> doctorsListed = doctorDao.listDoctors();

        assertEquals(DOCTORS_QUANTITY, doctorsListed.size());
        assertEquals(DOCTOR_ID, doctorsListed.get(0).getId());
        assertEquals(DOCTOR2_ID, doctorsListed.get(1).getId());
        assertEquals(DOCTOR3_ID, doctorsListed.get(2).getId());
    }

    @Test
    public void testSetDoctorSpecialty() {
        specialty1 = specialtyDao.findSpecialtyByName("OBSTETRICIA");
        specialty2 = specialtyDao.findSpecialtyByName("OFTALMOLOGÍA");
        Set<Specialty> specialties = new HashSet<>();
        specialties.add(specialty1);
        specialties.add(specialty2);
        Doctor d = doctorDao.findDoctorById(DOCTOR_ID).get();

        doctorDao.setDoctorSpecialty(d, specialties);

        assertTrue(d.getSpecialties().contains(specialty1));
        assertTrue(d.getSpecialties().contains(specialty2));
        assertEquals(SPECIALTIES_QUANTITY, specialties.size());
    }

    @Test
    public void testSetDoctorDescription() {
        Description descriptionDummy = descriptionDao.createDescription(CERTIFICATE, LANGUAGES, EDUCATION);
        Doctor doctor = doctorDao.findDoctorById(DOCTOR_ID).get();

        doctorDao.setDoctorDescription(doctor, descriptionDummy);

        assertEquals(descriptionDummy, doctor.getDescription());
    }

    @Test
    public void testFindDescriptionByDoctor() {
        Doctor doctor = doctorDao.findDoctorById(DOCTOR2_ID).get();

        doctorDao.findDescriptionByDoctor(doctor);

        assertEquals(CERTIFICATE2, doctor.getDescription().getCertificate());
        assertEquals(LANGUAGES2, doctor.getDescription().getLanguages());
        assertEquals(EDUCATION2, doctor.getDescription().getEducation());
    }


//    @Test
//    public void testFind() {
//        final Search search = new Search();
//        search.setName(DOC_SECOND_NAME);
//        search.setSpecialty(DOC_SPECIALTY);
//        search.setInsurance(DOC_INSURANCE);
//        List<String> insurancePlan = new ArrayList<>();
//        insurancePlan.add(DOC_INSURANCE_PLAN);
//        search.setInsurancePlan(insurancePlan);
//        search.setSex(DOC_SEX);
//        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(search);
//
//        assertTrue(filteredSearch.isPresent());
//        assertEquals(1, filteredSearch.get().getDoctors().size() );
//        assertEquals( DOC_NAME, filteredSearch.get().getDoctors().get(0).getFirstName() );
//        assertEquals( DOCTOR_ID, filteredSearch.get().getDoctors().get(0).getId() );
//        assertTrue( filteredSearch.get().getDoctors().get(0).getSpecialty().contains(DOC_SPECIALTY) );
//        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().containsKey(DOC_INSURANCE) );
//        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().get(DOC_INSURANCE).contains(DOC_INSURANCE_PLAN) );
//        assertEquals( DOC_SEX, filteredSearch.get().getDoctors().get(0).getSex() );
//    }

}