package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@Sql("classpath:doctorServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoctorServiceImplTest {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    private Doctor doctor, doctor2, doctor3;
    private Search search;

    private static final String DOC_NAME = "Roberto Nicolas Agustin";
    private static final String DOC_SECOND_NAME = "Nicolas";
    private static final Integer DOC_ID = 1;
    private static final String DOC_SPECIALTY = "NUTRICIÓN";
    private static final String DOC_INSURANCE = "Accord";
    private static final String DOC_INSURANCE_PLAN_AS_STRING = "('Accord Salud')";
    private static final String DOC_INSURANCE_PLAN = "Accord Salud";
    private static final String DOC_SEX = "M";

    private static final String NEW_DOC_NAME = "Marlon Jay";
    private static final String NEW_DOC_LASTNAME = "Brando";
    private static final String NEW_DOC_PHONE = "47771234";
    private static final String NEW_DOC_SEX = "M";
    private static final String NEW_DOC_LICENSE = "2020";
    private static final String NEW_DOC_AVATAR = "https://d1cesmq0xhh7we.cloudfront.net/cb5ddc05-1d68-48ca-a8ff-baba8239be85circle_medium__v1__.png";
    private static final String NEW_DOC_ADDRESS = "Cabildo 650";

    private static final Integer DOCTOR_QUANTITY_BEFORE = 3;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {

        jdbcTemplate = new JdbcTemplate(ds);

        doctor = Mockito.mock(Doctor.class);
        doctor2 = Mockito.mock(Doctor.class);
        doctor3 = Mockito.mock(Doctor.class);
        search = Mockito.mock(Search.class);
        when(doctor.getId()).thenReturn(1);
        when(doctor2.getId()).thenReturn(2);
        when(doctor3.getId()).thenReturn(3);

    }

    @After
    public void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "medicalCare", "doctorSpecialty", "doctor", "insurancePlan", "insurance",
                "Specialty", "review", "information");
    }

    @Test
    public void testList() {

        final Optional<CompressedSearch> doctors = doctorServiceImpl.listDoctors();

        assertNotNull(doctors.get());
        assertEquals(3, doctors.get().getDoctors().size());
        assertEquals(doctor.getId(), doctors.get().getDoctors().get(0).getId());
        assertEquals(doctor2.getId(), doctors.get().getDoctors().get(1).getId());
        assertEquals(doctor3.getId(), doctors.get().getDoctors().get(2).getId());

    }

    @Test
    public void testFind() {

        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append("%");
        nameBuilder.append(DOC_SECOND_NAME.toLowerCase());
        nameBuilder.append("%");

        List<String> insurancePlan = new ArrayList<>();
        insurancePlan.add(DOC_INSURANCE_PLAN);

        when(search.getName()).thenReturn(DOC_SECOND_NAME);
        when(search.getSimilarToName()).thenReturn(nameBuilder.toString());
        when(search.getSpecialty()).thenReturn(DOC_SPECIALTY);
        when(search.getInsurance()).thenReturn(DOC_INSURANCE);
        when(search.getInsurancePlanAsString()).thenReturn(DOC_INSURANCE_PLAN_AS_STRING);
        when(search.getSex()).thenReturn(DOC_SEX);

        final Optional<CompressedSearch> filteredSearch = doctorServiceImpl.findDoctors(search);

        assertTrue(filteredSearch.isPresent());
        assertEquals(1, filteredSearch.get().getDoctors().size() );
        assertEquals( DOC_NAME, filteredSearch.get().getDoctors().get(0).getFirstName() );
        assertEquals( DOC_ID, filteredSearch.get().getDoctors().get(0).getId() );
        assertTrue( filteredSearch.get().getDoctors().get(0).getSpecialty().contains(DOC_SPECIALTY) );
        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().containsKey(DOC_INSURANCE) );
        assertTrue( filteredSearch.get().getDoctors().get(0).getInsurance().get(DOC_INSURANCE).contains(DOC_INSURANCE_PLAN) );
        assertEquals( DOC_SEX, filteredSearch.get().getDoctors().get(0).getSex() );

    }

    @Test
    public void testFindById() {

       final Optional<Doctor> filteredById = doctorServiceImpl.findDoctorById(DOC_ID);

       assertTrue(filteredById.isPresent());
       assertEquals(doctor.getId(), filteredById.get().getId());

    }

    @Test
    public void testCreate() {

        final Doctor newDoctor = doctorServiceImpl.createDoctor(NEW_DOC_NAME, NEW_DOC_LASTNAME, NEW_DOC_PHONE, NEW_DOC_SEX, NEW_DOC_LICENSE, NEW_DOC_ADDRESS, NEW_DOC_AVATAR);

        assertNotNull(newDoctor);
        assertEquals(NEW_DOC_NAME, newDoctor.getFirstName());
        assertEquals(NEW_DOC_LASTNAME, newDoctor.getLastName());
        assertEquals(NEW_DOC_PHONE, newDoctor.getPhoneNumber());
        assertEquals(NEW_DOC_SEX, newDoctor.getSex());
        assertEquals(NEW_DOC_AVATAR, newDoctor.getAvatar());
        assertEquals(NEW_DOC_ADDRESS, newDoctor.getAddress());
        assertEquals(DOCTOR_QUANTITY_BEFORE + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "doctor"));

    }

}