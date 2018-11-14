package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.ListItem;
import ar.edu.itba.paw.models.Specialty;
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
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//@Sql("classpath:ServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SearchServiceImplTest {

    private static String INSURANCE_NAME_1 =  "Accord";
    private static String INSURANCE_NAME_2 =  "OSECAC";
    private static String INSURANCE_NAME_3 =  "OSPLAD";
    private static String INSURANCE_NAME_4 =  "OSDE";
    private static String INSURANCE_NAME_5 =  "OSPLA";

    private static Integer INSURANCE_ID_1 = 206;
    private static Integer INSURANCE_ID_2 = 210;
    private static Integer INSURANCE_ID_3 = 216;
    private static Integer INSURANCE_ID_4 = 217;
    private static Integer INSURANCE_ID_5 = 218;

    private static String SPECIALTY_NAME_1 = "NEURÓLOGO INFANTIL";
    private static String SPECIALTY_NAME_2 = "NUTRICIÓN";
    private static String SPECIALTY_NAME_3 = "OBSTETRICIA";
    private static String SPECIALTY_NAME_4 = "OFTALMOLOGÍA";
    private static String SPECIALTY_NAME_5 = "ONCOLOGÍA";

    private static Integer SPECIALTY_ID_1 = 551;
    private static Integer SPECIALTY_ID_2 = 552;
    private static Integer SPECIALTY_ID_3 = 553;
    private static Integer SPECIALTY_ID_4 = 554;
    private static Integer SPECIALTY_ID_5 = 555;

    private static int INSURANCES_QUANTITY = 5;
    private static int INSURANCE_PLANS_QUANTITY = 3;
    private static int SPECIALTIES_QUANTITY = 5;

    @Autowired
    private SearchServiceImpl searchServiceImpl;

    @Autowired
    private SearchDao searchDao;

    @Test
    public void testListInsurances() {

        List<Insurance> mockInsurances = new ArrayList<>();
        Insurance insurance1 = Mockito.mock(Insurance.class);
        Insurance insurance2 = Mockito.mock(Insurance.class);
        Insurance insurance3 = Mockito.mock(Insurance.class);
        Insurance insurance4 = Mockito.mock(Insurance.class);
        Insurance insurance5 = Mockito.mock(Insurance.class);
        mockInsurances.add(insurance1);
        mockInsurances.add(insurance2);
        mockInsurances.add(insurance3);
        mockInsurances.add(insurance4);
        mockInsurances.add(insurance5);
        when(insurance1.getId()).thenReturn(INSURANCE_ID_1);
        when(insurance2.getId()).thenReturn(INSURANCE_ID_2);
        when(insurance3.getId()).thenReturn(INSURANCE_ID_3);
        when(insurance4.getId()).thenReturn(INSURANCE_ID_4);
        when(insurance5.getId()).thenReturn(INSURANCE_ID_5);
        when(insurance1.getName()).thenReturn(INSURANCE_NAME_1);
        when(insurance2.getName()).thenReturn(INSURANCE_NAME_2);
        when(insurance3.getName()).thenReturn(INSURANCE_NAME_3);
        when(insurance4.getName()).thenReturn(INSURANCE_NAME_4);
        when(insurance5.getName()).thenReturn(INSURANCE_NAME_5);
        when(searchDao.listInsurances()).thenReturn(mockInsurances);

        List<Insurance> insurances = searchServiceImpl.listInsurances();

        assertNotNull(insurances);
        assertEquals(INSURANCES_QUANTITY, insurances.size());
        assertEquals(INSURANCE_ID_1, insurances.get(0).getId());
        assertEquals(INSURANCE_ID_2, insurances.get(1).getId());
        assertEquals(INSURANCE_ID_3, insurances.get(2).getId());
        assertEquals(INSURANCE_ID_4, insurances.get(3).getId());
        assertEquals(INSURANCE_ID_5, insurances.get(4).getId());
        assertEquals(INSURANCE_NAME_1, insurances.get(0).getName());
        assertEquals(INSURANCE_NAME_2, insurances.get(1).getName());
        assertEquals(INSURANCE_NAME_3, insurances.get(2).getName());
        assertEquals(INSURANCE_NAME_4, insurances.get(3).getName());
        assertEquals(INSURANCE_NAME_5, insurances.get(4).getName());

    }

    @Test
    public void testListInsurancePlans() {

        List<InsurancePlan> mockInsurancePlans = new ArrayList<>();
        InsurancePlan insurancePlan1 = Mockito.mock(InsurancePlan.class);
        InsurancePlan insurancePlan2 = Mockito.mock(InsurancePlan.class);
        InsurancePlan insurancePlan3 = Mockito.mock(InsurancePlan.class);
        mockInsurancePlans.add(insurancePlan1);
        mockInsurancePlans.add(insurancePlan2);
        mockInsurancePlans.add(insurancePlan3);
        when(searchDao.listInsurancePlans()).thenReturn(mockInsurancePlans);

        List<InsurancePlan> insurancePlans = searchServiceImpl.listInsurancePlans();

        assertTrue(!insurancePlans.isEmpty());
        assertEquals(INSURANCE_PLANS_QUANTITY, insurancePlans.size());
        assertTrue(insurancePlans.containsAll(mockInsurancePlans));

    }

    @Test
    public void testListSpecialties() {

        List<Specialty> mockSpecialties = new ArrayList<>();
        Specialty specialty1 = Mockito.mock(Specialty.class);
        Specialty specialty2 = Mockito.mock(Specialty.class);
        Specialty specialty3 = Mockito.mock(Specialty.class);
        Specialty specialty4 = Mockito.mock(Specialty.class);
        Specialty specialty5 = Mockito.mock(Specialty.class);
        mockSpecialties.add(specialty1);
        mockSpecialties.add(specialty2);
        mockSpecialties.add(specialty3);
        mockSpecialties.add(specialty4);
        mockSpecialties.add(specialty5);
        when(specialty1.getId()).thenReturn(SPECIALTY_ID_1);
        when(specialty2.getId()).thenReturn(SPECIALTY_ID_2);
        when(specialty3.getId()).thenReturn(SPECIALTY_ID_3);
        when(specialty4.getId()).thenReturn(SPECIALTY_ID_4);
        when(specialty5.getId()).thenReturn(SPECIALTY_ID_5);
        when(specialty1.getSpeciality()).thenReturn(SPECIALTY_NAME_1);
        when(specialty2.getSpeciality()).thenReturn(SPECIALTY_NAME_2);
        when(specialty3.getSpeciality()).thenReturn(SPECIALTY_NAME_3);
        when(specialty4.getSpeciality()).thenReturn(SPECIALTY_NAME_4);
        when(specialty5.getSpeciality()).thenReturn(SPECIALTY_NAME_5);
        when(searchDao.listSpecialties()).thenReturn(mockSpecialties);


        List<Specialty> specialties = searchServiceImpl.listSpecialties();

        assertNotNull(specialties);
        assertEquals(SPECIALTIES_QUANTITY, specialties.size());
        assertEquals(SPECIALTY_NAME_1, specialties.get(0).getSpeciality());
        assertEquals(SPECIALTY_NAME_2, specialties.get(1).getSpeciality());
        assertEquals(SPECIALTY_NAME_3, specialties.get(2).getSpeciality());
        assertEquals(SPECIALTY_NAME_4, specialties.get(3).getSpeciality());
        assertEquals(SPECIALTY_NAME_5, specialties.get(4).getSpeciality());
        assertEquals(SPECIALTY_ID_1, specialties.get(0).getId());
        assertEquals(SPECIALTY_ID_2, specialties.get(1).getId());
        assertEquals(SPECIALTY_ID_3, specialties.get(2).getId());
        assertEquals(SPECIALTY_ID_4, specialties.get(3).getId());
        assertEquals(SPECIALTY_ID_5, specialties.get(4).getId());

    }

}
