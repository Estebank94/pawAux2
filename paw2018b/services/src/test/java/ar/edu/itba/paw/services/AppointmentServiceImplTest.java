

package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.persistance.AppointmentDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentServiceImplTest {

    private static final LocalTime TIME = LocalTime.of(9, 10, 50);
    private static final LocalDate DATE = LocalDate.of(2017, Month.JANUARY, 1);
    private static final Integer DOC_ID = 1;
    private static final Integer PATIENT_ID = 5;

    private Doctor doctor;
    private Patient patient;
    private Appointment appointment;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    private AppointmentDao appointmentDao;

    @Test
    public void testCreateAppointment() throws Exception {

        doctor = Mockito.mock(Doctor.class);
        patient = Mockito.mock(Patient.class);
        appointment = Mockito.mock(Appointment.class);
        when(appointment.getAppointmentDay()).thenReturn(DATE.toString());
        when(appointment.getAppointmentTime()).thenReturn(TIME.toString());
        when(appointment.getPatient()).thenReturn(patient);
        when(appointment.getDoctor()).thenReturn(doctor);
        when(patient.getId()).thenReturn(PATIENT_ID);
        when(doctor.getId()).thenReturn(DOC_ID);
        when(appointmentDao.findAppointment(DATE.toString(), TIME.toString(), patient, doctor)).thenReturn(Optional.empty());
        when(appointmentDao.createAppointment(DATE.toString(), TIME.toString(), patient, doctor)).thenReturn(appointment);

        final Appointment appointment = appointmentServiceImpl.createAppointment(DATE.toString(), TIME.toString(), patient, doctor);

        assertEquals(DATE.toString(), appointment.getAppointmentDay());
        assertEquals(TIME.toString(), appointment.getAppointmentTime());
        assertEquals(DOC_ID, appointment.getDoctor().getId());
        assertEquals(PATIENT_ID, appointment.getPatient().getId());

    }

}
