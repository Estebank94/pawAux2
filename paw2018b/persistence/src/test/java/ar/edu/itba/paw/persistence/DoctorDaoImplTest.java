package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Description;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;

import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
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

import javax.sql.DataSource;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Sql("classpath:doctorDaoTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoctorDaoImplTest {

    private static final String NEW_DOC_NAME = "Marlon Jay";
    private static final String NEW_DOC_LASTNAME = "Brando";
    private static final String NEW_DOC_PHONE = "47771234";
    private static final String NEW_DOC_SEX = "M";
    private static final String NEW_DOC_LICENSE = "2020";
    private static final String NEW_DOC_AVATAR = "https://d1cesmq0xhh7we.cloudfront.net/cb5ddc05-1d68-48ca-a8ff-baba8239be85circle_medium__v1__.png";
    private static final String NEW_DOC_ADDRESS = "Cabildo 650";

    private static final String DOC_NAME = "Roberto Nicolas Agustin";
    private static final String DOC_SPECIALTY = "NUTRICIÓN";
    private static final String DOC_INSURANCE = "Accord";
    private static final String DOC_INSURANCE_PLAN = "Accord Salud";
    private static final String DOC_SEX = "M";

    private static final String DOC_SEX_FEMALE = "F";

    private static final Integer DOCTOR_QUANTITY_BEFORE = 3;
    private static final String DOC_SECOND_NAME = "Nicolas";
    private static final Integer DOCTOR_ID = 1;

    @Autowired
    private DataSource ds;

    @Autowired
    private DoctorDaoImpl doctorDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @After
    public void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "medicalCare", "doctorSpecialty", "doctor", "insurancePlan", "insurance",
                "Specialty", "review", "information");
    }

    @Test
    public void testCreate() {
        Doctor doctor = null;
        try {
             doctor = doctorDao.createDoctor(NEW_DOC_NAME, NEW_DOC_LASTNAME, NEW_DOC_PHONE, NEW_DOC_SEX, NEW_DOC_LICENSE, NEW_DOC_ADDRESS, NEW_DOC_AVATAR);
        } catch (NotCreateDoctorException e) {
        } catch (RepeatedLicenceException e) {
        }
        assertNotNull(doctor);
        assertEquals(NEW_DOC_NAME, doctor.getFirstName());
        assertEquals(NEW_DOC_LASTNAME, doctor.getLastName());
        assertEquals(NEW_DOC_PHONE, doctor.getPhoneNumber());
        assertEquals(NEW_DOC_SEX, doctor.getSex());
        assertEquals(NEW_DOC_AVATAR, doctor.getAvatar());
        assertEquals(NEW_DOC_ADDRESS, doctor.getAddress());
        assertEquals(DOCTOR_QUANTITY_BEFORE + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "doctor"));
    }

    @Test
    public void testFindByName() {
        final Search searchByName = new Search();
        searchByName.setName(DOC_SECOND_NAME);
        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(searchByName);

        assertTrue(filteredSearch.isPresent());
        assertEquals(1, filteredSearch.get().getDoctors().size() );
        assertEquals( DOC_NAME, filteredSearch.get().getDoctors().get(0).getFirstName() );
        assertEquals( DOCTOR_ID, filteredSearch.get().getDoctors().get(0).getId() );
    }

    @Test
    public void testFindBySpecialty() {
        final Search searchBySpecialty = new Search();
        searchBySpecialty.setSpecialty(DOC_SPECIALTY);
        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(searchBySpecialty);

        assertTrue(filteredSearch.isPresent());
        assertEquals(1, filteredSearch.get().getDoctors().size() );
        assertTrue( filteredSearch.get().getDoctors().get(0).getSpecialty().contains(DOC_SPECIALTY) );
    }

    @Test
    public void testFindByInsurance() {
        final Search searchByInsurance = new Search();
        searchByInsurance.setInsurance(DOC_INSURANCE);
        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(searchByInsurance);

        assertTrue(filteredSearch.isPresent());
        assertEquals(2, filteredSearch.get().getDoctors().size() );
        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().containsKey(DOC_INSURANCE) );
    }

    @Test
    public void testFindByInsurancePlan() {
        final Search searchByInsurancePlan = new Search();
        searchByInsurancePlan.setInsurance(DOC_INSURANCE);
        List<String> insurancePlan = new ArrayList<>();
        insurancePlan.add(DOC_INSURANCE_PLAN);
        searchByInsurancePlan.setInsurancePlan(insurancePlan);
        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(searchByInsurancePlan);

        assertTrue(filteredSearch.isPresent());
        assertEquals(1, filteredSearch.get().getDoctors().size() );
        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().get(DOC_INSURANCE).contains(DOC_INSURANCE_PLAN) );
    }

    @Test
    public void testFindBySex() {
        final Search searchBySex = new Search();
        searchBySex.setSex(DOC_SEX_FEMALE);
        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(searchBySex);

        assertTrue(filteredSearch.isPresent());
        assertEquals(1, filteredSearch.get().getDoctors().size() );
        assertEquals( DOC_SEX_FEMALE, filteredSearch.get().getDoctors().get(0).getSex() );
    }

    @Test
    public void testFind() {
        final Search search = new Search();
        search.setName(DOC_SECOND_NAME);
        search.setSpecialty(DOC_SPECIALTY);
        search.setInsurance(DOC_INSURANCE);
        List<String> insurancePlan = new ArrayList<>();
        insurancePlan.add(DOC_INSURANCE_PLAN);
        search.setInsurancePlan(insurancePlan);
        search.setSex(DOC_SEX);
        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(search);

        assertTrue(filteredSearch.isPresent());
        assertEquals(1, filteredSearch.get().getDoctors().size() );
        assertEquals( DOC_NAME, filteredSearch.get().getDoctors().get(0).getFirstName() );
        assertEquals( DOCTOR_ID, filteredSearch.get().getDoctors().get(0).getId() );
        assertTrue( filteredSearch.get().getDoctors().get(0).getSpecialty().contains(DOC_SPECIALTY) );
        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().containsKey(DOC_INSURANCE) );
        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().get(DOC_INSURANCE).contains(DOC_INSURANCE_PLAN) );
        assertEquals( DOC_SEX, filteredSearch.get().getDoctors().get(0).getSex() );
    }

}