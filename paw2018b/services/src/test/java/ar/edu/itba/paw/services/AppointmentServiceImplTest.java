

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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentServiceImplTest {

    private static final LocalTime CREATE_TIME = LocalTime.of(9, 10, 50);
    private static final LocalDate CREATE_DATE = LocalDate.of(2017, Month.JANUARY, 1);
    private static final LocalTime CANCEL_TIME = LocalTime.of(9, 10, 50);
    private static final LocalDate CANCEL_DATE = LocalDate.of(2017, Month.JANUARY, 1);
    private static final Integer DOC_ID = 1;
    private static final Integer PATIENT_ID = 5;
    private static final Integer APPOINTMENT_ID = 11;

    private Doctor createDoctor, cancelDoctor;
    private Patient createPatient, cancelPatient;
    private Appointment appointment, cancelAppointment, findAppointment;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    private AppointmentDao appointmentDao;

    @Test
    public void testCreateAppointment() throws Exception {
        createDoctor = Mockito.mock(Doctor.class);
        createPatient = Mockito.mock(Patient.class);
        appointment = Mockito.mock(Appointment.class);
        when(appointment.getAppointmentDay()).thenReturn(CREATE_DATE.toString());
        when(appointment.getAppointmentTime()).thenReturn(CREATE_TIME.toString());
        when(appointment.getPatient()).thenReturn(createPatient);
        when(appointment.getDoctor()).thenReturn(createDoctor);
        when(createPatient.getId()).thenReturn(PATIENT_ID);
        when(createDoctor.getId()).thenReturn(DOC_ID);
        when(appointmentDao.findAppointment(CREATE_DATE.toString(), CREATE_TIME.toString(), createPatient, createDoctor))
                .thenReturn(Optional.empty());
        when(appointmentDao.createAppointment(CREATE_DATE.toString(), CREATE_TIME.toString(), createPatient, createDoctor))
                .thenReturn(appointment);

        final Appointment appointment = appointmentServiceImpl.createAppointment(CREATE_DATE.toString(),
                CREATE_TIME.toString(), createPatient, createDoctor);

        assertEquals(CREATE_DATE.toString(), appointment.getAppointmentDay());
        assertEquals(CREATE_TIME.toString(), appointment.getAppointmentTime());
        assertEquals(DOC_ID, appointment.getDoctor().getId());
        assertEquals(PATIENT_ID, appointment.getPatient().getId());
    }

    @Test
    public void testCancelAppointment() throws Exception{
        cancelDoctor = Mockito.mock(Doctor.class);
        cancelPatient = Mockito.mock(Patient.class);
        cancelAppointment = Mockito.mock(Appointment.class);
        List<Appointment> canceledAppointment = new LinkedList<>();
        canceledAppointment.add(cancelAppointment);
        when(cancelPatient.getId()).thenReturn(PATIENT_ID);
        when(cancelPatient.getPatientId()).thenReturn(PATIENT_ID);
        when(cancelAppointment.getPatient()).thenReturn(cancelPatient);
        when(appointmentDao.findAppointmentByTime(CANCEL_DATE.toString(), CANCEL_TIME.toString(), cancelDoctor)).thenReturn(canceledAppointment);
        when(cancelAppointment.getAppointmentCancelled()).thenReturn(false);

        Appointment appointmentCancelled = appointmentServiceImpl.cancelAppointment(CANCEL_DATE.toString(),
                CANCEL_TIME.toString(), cancelPatient, cancelDoctor);

        assertEquals(cancelAppointment, appointmentCancelled);
    }

    @Test
    public void testFindAppointmentById() throws Exception{
        findAppointment = Mockito.mock(Appointment.class);
        when(appointmentDao.findAppointmentById(APPOINTMENT_ID)).thenReturn(Optional.of(findAppointment));
        when(findAppointment.getId()).thenReturn(APPOINTMENT_ID);

        Appointment appointmentFound = appointmentServiceImpl.findAppointmentById(APPOINTMENT_ID);

        assertEquals(appointmentFound.getId(), APPOINTMENT_ID);
    }
}
