package ar.edu.itba.paw.services;


import ar.edu.itba.paw.models.Appointment;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.*;

@Sql("classpath:ServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentServiceImplTest {

    private static final LocalTime TIME = LocalTime.of(9, 10, 50);
    private static final LocalDate DATE = LocalDate.of(2017, Month.JANUARY, 1);
    private static final Integer DOC_ID = 1;
    private static final Integer PATIENT_ID = 5;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {

        jdbcTemplate = new JdbcTemplate(ds);

    }

    @After
    public void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "appointment", "workingHour", "medicalCare", "doctorSpecialty", "doctor", "insurancePlan", "insurance",
                "Specialty", "review", "information", "patient");
    }

    @Test
    public void testCreateAppointment() throws Exception {


        final Optional<Appointment> appointment = appointmentServiceImpl.createAppointment(DOC_ID, PATIENT_ID, DATE, TIME);

        assertTrue(appointment.isPresent());
        assertEquals(DATE, appointment.get().getAppointmentDay());
        assertEquals(TIME, appointment.get().getAppointmentTime());
        assertEquals(DOC_ID, appointment.get().getDoctorId());
        assertEquals(PATIENT_ID, appointment.get().getClientId());

    }

}
