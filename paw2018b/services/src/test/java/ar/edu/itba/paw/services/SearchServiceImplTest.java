//package ar.edu.itba.paw.services;
//
//import ar.edu.itba.paw.models.InsurancePlan;
//import ar.edu.itba.paw.models.ListItem;
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
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@Sql("classpath:ServiceTest.sql")
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class SearchServiceImplTest {
//
//    private static String INSURANCE_NAME_1 =  "Accord";
//    private static String INSURANCE_NAME_2 =  "OSECAC";
//    private static String INSURANCE_NAME_3 =  "OSPLAD";
//    private static String INSURANCE_NAME_4 =  "OSDE";
//    private static String INSURANCE_NAME_5 =  "OSPLA";
//
//    private static Integer INSURANCE_ID_1 = 206;
//    private static Integer INSURANCE_ID_2 = 210;
//    private static Integer INSURANCE_ID_3 = 216;
//    private static Integer INSURANCE_ID_4 = 217;
//    private static Integer INSURANCE_ID_5 = 218;
//
//    private static String INSURANCE_PLAN_NAME_1 =  "Accord Salud";
//    private static String INSURANCE_PLAN_NAME_2 =  "210";
//    private static String INSURANCE_PLAN_NAME_3 =  "310";
//    private static String INSURANCE_PLAN_NAME_4 =  "410";
//
//    private static String SPECIALTY_NAME_1 = "NEURÓLOGO INFANTIL";
//    private static String SPECIALTY_NAME_2 = "NUTRICIÓN";
//    private static String SPECIALTY_NAME_3 = "OBSTETRICIA";
//    private static String SPECIALTY_NAME_4 = "OFTALMOLOGÍA";
//    private static String SPECIALTY_NAME_5 = "ONCOLOGÍA";
//
//    private static Integer SPECIALTY_ID_1 = 551;
//    private static Integer SPECIALTY_ID_2 = 552;
//    private static Integer SPECIALTY_ID_3 = 553;
//    private static Integer SPECIALTY_ID_4 = 554;
//    private static Integer SPECIALTY_ID_5 = 555;
//
//    private static int INSURANCES_QUANTITY = 5;
//    private static int INSURANCE_PLANS_QUANTITY = 3;
//    private static int INSURANCES_WITH_DOCTORS = 3;
//    private static int SPECIALTIES_QUANTITY = 5;
//    private static int SPECIALTIES_WITH_DOCTORS = 3;
//
//    @Autowired
//    private SearchServiceImpl searchServiceImpl;
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
//    public void testListInsurances() {
//
//        Optional<List<ListItem>> insurances = searchServiceImpl.listInsurances();
//
//        assertTrue(insurances.isPresent());
//        assertEquals(INSURANCES_QUANTITY, insurances.get().size());
//        assertEquals(INSURANCE_ID_1, insurances.get().get(0).getId());
//        assertEquals(INSURANCE_ID_2, insurances.get().get(1).getId());
//        assertEquals(INSURANCE_ID_3, insurances.get().get(2).getId());
//        assertEquals(INSURANCE_ID_4, insurances.get().get(3).getId());
//        assertEquals(INSURANCE_ID_5, insurances.get().get(4).getId());
//        assertEquals(INSURANCE_NAME_1, insurances.get().get(0).getName());
//        assertEquals(INSURANCE_NAME_2, insurances.get().get(1).getName());
//        assertEquals(INSURANCE_NAME_3, insurances.get().get(2).getName());
//        assertEquals(INSURANCE_NAME_4, insurances.get().get(3).getName());
//        assertEquals(INSURANCE_NAME_5, insurances.get().get(4).getName());
//
//    }
//
//    @Test
//    public void testListInsurancePlans() {
//
//        List<InsurancePlan> insurancePlans = searchServiceImpl.listInsurancePlans();
//
//        assertTrue(!insurancePlans.isEmpty());
//        assertEquals(INSURANCE_PLANS_QUANTITY, insurancePlans.get().size());
//        assertTrue(insurancePlans.get().containsKey(INSURANCE_NAME_1));
//        assertTrue(insurancePlans.get().containsKey(INSURANCE_NAME_2));
//        assertTrue(insurancePlans.get().containsKey(INSURANCE_NAME_3));
//        assertTrue(insurancePlans.get().get(INSURANCE_NAME_1).contains(INSURANCE_PLAN_NAME_1));
//        assertTrue(insurancePlans.get().get(INSURANCE_NAME_1).contains(INSURANCE_PLAN_NAME_4));
//        assertTrue(insurancePlans.get().get(INSURANCE_NAME_2).contains(INSURANCE_PLAN_NAME_2));
//        assertTrue(insurancePlans.get().get(INSURANCE_NAME_3).contains(INSURANCE_PLAN_NAME_3));
//
//    }
//
//    @Test
//    public void testInsuranceWithDoctors() {
//
//        Optional<List<ListItem>> insurancesWithDoctors = searchServiceImpl.listInsurancesWithDoctors();
//
//        assertTrue(insurancesWithDoctors.isPresent());
//        assertEquals(INSURANCES_WITH_DOCTORS,insurancesWithDoctors.get().size());
//        assertEquals(INSURANCE_ID_1, insurancesWithDoctors.get().get(0).getId());
//        assertEquals(INSURANCE_ID_2, insurancesWithDoctors.get().get(1).getId());
//        assertEquals(INSURANCE_ID_3, insurancesWithDoctors.get().get(2).getId());
//        assertEquals(INSURANCE_NAME_1, insurancesWithDoctors.get().get(0).getName());
//        assertEquals(INSURANCE_NAME_2, insurancesWithDoctors.get().get(1).getName());
//        assertEquals(INSURANCE_NAME_3, insurancesWithDoctors.get().get(2).getName());
//
//    }
//
//    @Test
//    public void testListSpecialties() {
//
//        Optional<List<ListItem>> specialties = searchServiceImpl.listSpecialties();
//
//        assertTrue(specialties.isPresent());
//        assertEquals(SPECIALTIES_QUANTITY, specialties.get().size());
//        assertEquals(SPECIALTY_NAME_1, specialties.get().get(0).getName());
//        assertEquals(SPECIALTY_NAME_2, specialties.get().get(1).getName());
//        assertEquals(SPECIALTY_NAME_3, specialties.get().get(2).getName());
//        assertEquals(SPECIALTY_NAME_4, specialties.get().get(3).getName());
//        assertEquals(SPECIALTY_NAME_5, specialties.get().get(4).getName());
//        assertEquals(SPECIALTY_ID_1, specialties.get().get(0).getId());
//        assertEquals(SPECIALTY_ID_2, specialties.get().get(1).getId());
//        assertEquals(SPECIALTY_ID_3, specialties.get().get(2).getId());
//        assertEquals(SPECIALTY_ID_4, specialties.get().get(3).getId());
//        assertEquals(SPECIALTY_ID_5, specialties.get().get(4).getId());
//
//    }
//
//    @Test
//    public void testListSpecialtiesWithDoctors() {
//
//        Optional<List<ListItem>> specialtiesWithDoctors = searchServiceImpl.listSpecialtiesWithDoctors();
//
//        assertTrue(specialtiesWithDoctors.isPresent());
//        assertEquals(SPECIALTIES_WITH_DOCTORS, specialtiesWithDoctors.get().size());
//        assertEquals(SPECIALTY_NAME_1, specialtiesWithDoctors.get().get(0).getName());
//        assertEquals(SPECIALTY_NAME_2, specialtiesWithDoctors.get().get(1).getName());
//        assertEquals(SPECIALTY_NAME_5, specialtiesWithDoctors.get().get(2).getName());
//        assertEquals(SPECIALTY_ID_1, specialtiesWithDoctors.get().get(0).getId());
//        assertEquals(SPECIALTY_ID_2, specialtiesWithDoctors.get().get(1).getId());
//        assertEquals(SPECIALTY_ID_5, specialtiesWithDoctors.get().get(2).getId());
//
//    }
//
//}
