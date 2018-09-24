package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Description;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;

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

    private static final int DOCTOR_ID = 1;
//    private List mockedList = mock(List.class);
    private List<String> insurancePlan = new ArrayList<>();
    private Search search = new Search();
    private static final String TEST_SPECIALTY = "NUTRICION";
    private static final String TEST_NAME = "Roberto";
    private static final String TEST_SEX = "M";
    private Set<String> specialty = new HashSet<>();
    private Map<String, Set<String>> insuranceMap = new HashMap<>();
    private Set<String> certificate = new HashSet<>();
    private Set<String> languages = new HashSet<>();
    private Set<String> education = new HashSet<>();
    private Description description = new Description(certificate, languages, education);
    private Doctor doctor = new Doctor(TEST_NAME, "Rosa", TEST_SEX, "Arce 211",
            "https://d1cesmq0xhh7we.cloudfront.net/724f4a59-0f34-4cbc-980f-766f4df17d9bcircle_medium__v1__.png",
            specialty, insuranceMap,"hours", DOCTOR_ID, description, "47777777");
    private List<Doctor> doctors = new ArrayList<>();
    private CompressedSearch expected = new CompressedSearch();

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

//        Search search = new Search("Roberto","NUTRICION","101", "M", insurancePlan);
//        insurancePlan.add("Accord Salud");
//        search.setSex("ALL");
//        search.setInsurancePlan(insurancePlan);
//        search.setInsurance("no");
////        search.setLocation("Palermo");
//        search.setSpecialty("");
        search.setName("Roberto");
        specialty.add(TEST_SPECIALTY);
        certificate.add("Master");
        languages.add("ingles");
        education.add("UBA");
        doctors.add(doctor);
        expected.setDoctors(doctors);


        final Optional<CompressedSearch> filteredSearch = doctorDao.findDoctors(search);

        assertTrue( expected.getDoctors().containsAll(filteredSearch.get().getDoctors()));

    }

}