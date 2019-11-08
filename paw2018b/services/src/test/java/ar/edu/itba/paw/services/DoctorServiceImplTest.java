package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.DoctorDao;
import ar.edu.itba.paw.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@Sql("classpath:ServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DoctorServiceImplTest {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    private Doctor doctor, doctor2, doctor3, createDoctor;
    private WorkingHours workingHours;
    private Description description;
    private Search search;
    private Specialty specialty;
    private Insurance insurance;

//    private static final String DOC_NAME = "Roberto Nicolas Agustin";
//    private static final String DOC_SECOND_NAME = "Nicolas";
    private static final Integer DOC_ID = 1;
    private static final String DOC_SPECIALTY = "NUTRICIÓN";
    private static final String DOC_INSURANCE = "Accord";
//    private static final String DOC_INSURANCE_PLAN_AS_STRING = "('Accord Salud')";
    private static final String DOC_INSURANCE_PLAN = "Accord Salud";
//    private static final String DOC_SEX = "M";

    private static final String NEW_DOC_NAME = "Marlon Jay";
    private static final String NEW_DOC_LASTNAME = "Brando";
    private static final String NEW_DOC_PHONE = "47771234";
    private static final String NEW_DOC_SEX = "M";
    private static final String NEW_DOC_LICENSE = String.valueOf(2020);
    private static final Integer NEW_DOC_LICENSE_INT = 2020;
    private static final byte NEW_DOC_AVATAR [] = new byte[] {1, 6, 3};
    private static final String NEW_DOC_ADDRESS = "Cabildo 650";

    private static final DayOfWeek DAY_OF_WEEK = DayOfWeek.FRIDAY;
    private static final LocalTime START = LocalTime.of(9, 10, 50);
    private static final LocalTime END = LocalTime.of(11, 45, 20);
    private static final String CERTIFICATE = "BACHELOR";
    private static final String EDUCATION = "ITBA";
    private static final String LANGUAGE = "English";

    @Autowired
    private DoctorDao doctorDao;

    @Before
    public void setUp() {

        doctor = Mockito.mock(Doctor.class);
        doctor2 = Mockito.mock(Doctor.class);
        doctor3 = Mockito.mock(Doctor.class);
        search = Mockito.mock(Search.class);
        when(doctor.getId()).thenReturn(1);
        when(doctor2.getId()).thenReturn(2);
        when(doctor3.getId()).thenReturn(3);

    }

//   Consultar error con el merge
//    @Test
//    @Transactional
//    public void testFindById() throws Exception{
//
//        when(doctorDao.findDoctorById(DOC_ID)).thenReturn(Optional.of(doctor));
//        Mockito.when(em.getTransaction()).thenReturn(et);
////        Mockito.when(em.isJoinedToTransaction()).thenReturn(true);
////        Mockito.when(em.isOpen()).thenReturn(true);
////        Mockito.when(em.merge(doctorDao.findDoctorById(Integer.parseInt(DOC_ID.toString())).get())).thenReturn(doctor);
//        Mockito.when(emf.createEntityManager()).thenReturn(em);
//
//        Optional<Doctor> filteredById =  doctorServiceImpl.findDoctorById(DOC_ID.toString());
//
//        assertTrue(filteredById.isPresent());
//        assertEquals(doctor.getId(), filteredById.get().getId());
//
//    }

    @Test
    public void testCreate() throws Exception {

        createDoctor = Mockito.mock(Doctor.class);
        when(doctorDao.createDoctor(NEW_DOC_NAME, NEW_DOC_LASTNAME, NEW_DOC_PHONE, NEW_DOC_SEX, Integer.parseInt(NEW_DOC_LICENSE), NEW_DOC_AVATAR, NEW_DOC_ADDRESS)).thenReturn(createDoctor);
        when(createDoctor.getFirstName()).thenReturn(NEW_DOC_NAME);
        when(createDoctor.getLastName()).thenReturn(NEW_DOC_LASTNAME);
        when(createDoctor.getPhoneNumber()).thenReturn(NEW_DOC_PHONE);
        when(createDoctor.getSex()).thenReturn(NEW_DOC_SEX);
        when(createDoctor.getLicence()).thenReturn(NEW_DOC_LICENSE_INT);
        when(createDoctor.getAddress()).thenReturn(NEW_DOC_ADDRESS);
        when(createDoctor.getProfilePicture()).thenReturn(NEW_DOC_AVATAR);

        Doctor newDoctor = doctorServiceImpl.createDoctor(NEW_DOC_NAME, NEW_DOC_LASTNAME, NEW_DOC_PHONE, NEW_DOC_SEX, NEW_DOC_LICENSE, NEW_DOC_AVATAR, NEW_DOC_ADDRESS);

        assertNotNull(newDoctor);
        assertEquals(NEW_DOC_NAME, newDoctor.getFirstName());
        assertEquals(NEW_DOC_LASTNAME, newDoctor.getLastName());
        assertEquals(NEW_DOC_PHONE, newDoctor.getPhoneNumber());
        assertEquals(NEW_DOC_SEX, newDoctor.getSex());
        assertEquals(NEW_DOC_AVATAR, newDoctor.getProfilePicture());
        assertEquals(NEW_DOC_ADDRESS, newDoctor.getAddress());
        assertEquals(NEW_DOC_LICENSE_INT, newDoctor.getLicence());
    }

    @Test
    public void testSetDoctorInfo() throws Exception{

        workingHours = Mockito.mock(WorkingHours.class);
        description = Mockito.mock(Description.class);
        specialty = Mockito.mock(Specialty.class);
        insurance = Mockito.mock(Insurance.class);

        Set<Specialty> specialtySet = new HashSet<>();
        specialtySet.add(specialty);
        Set<Insurance> insurances = new HashSet<>();
        insurances.add(insurance);
        Set<String> insurancePlanSet = new HashSet<>();
        insurancePlanSet.add(DOC_INSURANCE_PLAN);
        Map<String, Set<String>> insuranceMap = new HashMap<>();
        insuranceMap.put(DOC_INSURANCE, insurancePlanSet);
        List<WorkingHours> workingHoursList = new ArrayList<>();
        workingHoursList.add(workingHours);
        Map<DayOfWeek, List<WorkingHours>> workingHoursMap = new HashMap<>();
        workingHoursMap.put(DAY_OF_WEEK, workingHoursList);

        when(doctorDao.findDoctorById(DOC_ID)).thenReturn(Optional.of(doctor));
        when(doctor.getSpecialties()).thenReturn(specialtySet);
        when(workingHours.getStartTime()).thenReturn(START.toString());
        when(workingHours.getFinishTime()).thenReturn(END.toString());
        when(workingHours.getDayOfWeek()).thenReturn(DAY_OF_WEEK.getValue());
        when(description.getCertificate()).thenReturn(CERTIFICATE);
        when(description.getEducation()).thenReturn(EDUCATION);
        when(description.getLanguages()).thenReturn(LANGUAGE);
        when(specialty.getSpeciality()).thenReturn(DOC_SPECIALTY);
        when(insurance.getName()).thenReturn(DOC_INSURANCE);

        final Optional<Doctor> setDoctor = doctorServiceImpl.setDoctorInfo(DOC_ID, specialtySet, insurances, workingHoursList, description);

        assertTrue(setDoctor.isPresent());
        assertEquals(DOC_ID, setDoctor.get().getId());

    }

}