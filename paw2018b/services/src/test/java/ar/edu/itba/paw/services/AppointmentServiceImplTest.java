

package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.AppointmentDao;
import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@Sql("classpath:ServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentServiceImplTest {

    private static final LocalTime TIME = LocalTime.of(9, 10, 50);
    private static final LocalDate DATE = LocalDate.of(2017, Month.JANUARY, 1);
    private static final Integer DOC_ID = 1;
    private static final Integer PATIENT_ID = 5;

//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();
//
//    @InjectMocks
//    private AppointmentDao appointmentDao;
//
//    @InjectMocks
//    private DoctorDao doctorDao;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    private Doctor doctor;

    @Autowired
    private Patient patient;

    @Autowired
    private Appointment appointment;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {

        jdbcTemplate = new JdbcTemplate(ds);
//        MockitoAnnotations.initMocks(this);
        doctor = Mockito.mock(Doctor.class);
        patient = Mockito.mock(Patient.class);
        when(appointment.getAppointmentDay()).thenReturn(DATE.toString());
        when(appointment.getAppointmentTime()).thenReturn(TIME.toString());
        when(appointment.getPatient()).thenReturn(patient);
        when(appointment.getDoctor()).thenReturn(doctor);
        when(patient.getId()).thenReturn(PATIENT_ID);
        when(doctor.getId()).thenReturn(DOC_ID);

        try {
            when(appointmentDao.createAppointment(DATE.toString(), TIME.toString(), patient, doctor)).thenReturn(appointment);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "appointment", "workingHour", "medicalCare", "doctorSpecialty", "doctor", "insurancePlan", "insurance",
                "Specialty", "review", "information", "patient");
    }

    @Test
    public void testCreateAppointment() throws Exception {

        final Appointment appointment = appointmentServiceImpl.createAppointment(DATE.toString(), TIME.toString(), patient, doctor);

        assertEquals(DATE.toString(), appointment.getAppointmentDay());
        assertEquals(TIME.toString(), appointment.getAppointmentTime());
        assertEquals(DOC_ID, appointment.getDoctor().getId());
        assertEquals(PATIENT_ID, appointment.getPatient().getId());

    }

}
