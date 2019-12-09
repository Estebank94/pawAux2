package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.PatientDao;
import ar.edu.itba.paw.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

//@Sql("classpath:ServiceTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PatientServiceImplTest {

    private static final String PATIENT_EMAIL = "Roberto@rosa.com";
    private static final Integer PATIENT_ID = 5;
    private static final Integer DOC_ID = 1;
    private static final String TOKEN = "token";

    private static final String NEW_PATIENT_NAME = "Mike";
    private static final String NEW_PATIENT_LASTNAME = "Muchpain";
    private static final String NEW_PATIENT_PHONE = "1512341234";
    private static final String NEW_PATIENT_EMAIL = "mike@lovewaldoc.com";
    private static final String NEW_PATIENT_PASSWORD = "SecretPass";

    private Patient createPatient, setDoctorIdPatient, findByIdPatient, findByEmailPatient, createTokenPatient,
            enablePatient, deletePatient, historicalAppointmentPatient, futureAppointmentPatient, favoritePatient;

    private Verification createVerification, findVerification;

    private Appointment appointment1, appointment2, futureAppointment1, futureAppointment2;

    private Favorite favDoctor1, favDoctor2;

    private Doctor doctor;

    @Autowired
    private PatientServiceImpl patientServiceImpl;

    @Autowired
    private PatientDao patientDao;

    @Test
    public void testCreatePatient() throws Exception {
        createPatient = Mockito.mock(Patient.class);
        when(patientDao.createPatient(NEW_PATIENT_NAME, NEW_PATIENT_LASTNAME, NEW_PATIENT_PHONE,
                NEW_PATIENT_EMAIL, NEW_PATIENT_PASSWORD)).thenReturn(createPatient);
        when(createPatient.getFirstName()).thenReturn(NEW_PATIENT_NAME);
        when(createPatient.getLastName()).thenReturn(NEW_PATIENT_LASTNAME);
        when(createPatient.getPhoneNumber()).thenReturn(NEW_PATIENT_PHONE);
        when(createPatient.getEmail()).thenReturn(NEW_PATIENT_EMAIL);
        when(createPatient.getPassword()).thenReturn(NEW_PATIENT_PASSWORD);

        Patient newPatient = patientServiceImpl.createPatient(NEW_PATIENT_NAME, NEW_PATIENT_LASTNAME,
                NEW_PATIENT_PHONE, NEW_PATIENT_EMAIL, NEW_PATIENT_PASSWORD);

        assertNotNull(newPatient);
        assertEquals(NEW_PATIENT_NAME, newPatient.getFirstName());
        assertEquals(NEW_PATIENT_LASTNAME, newPatient.getLastName());
        assertEquals(NEW_PATIENT_PHONE, newPatient.getPhoneNumber());
        assertEquals(NEW_PATIENT_EMAIL, newPatient.getEmail());
        assertEquals(NEW_PATIENT_PASSWORD, newPatient.getPassword());
    }

    @Test
    public void testSetDoctorId() throws Exception {
        testSetDoctorId(true);
        testSetDoctorId(false);
    }

    public void testSetDoctorId(Boolean setAnswer) throws Exception {
        setDoctorIdPatient = Mockito.mock(Patient.class);
        doctor = Mockito.mock(Doctor.class);
        when(patientDao.setDoctorId(setDoctorIdPatient, doctor)).thenReturn(setAnswer);
        when(setDoctorIdPatient.getPatientId()).thenReturn(PATIENT_ID);
        when(doctor.getId()).thenReturn(DOC_ID);
        when(setDoctorIdPatient.getDoctor()).thenReturn(doctor);

        Boolean state = patientServiceImpl.setDoctorId(setDoctorIdPatient,doctor);

        assertEquals(state, setAnswer);
        assertEquals(setDoctorIdPatient.getDoctor().getId(), DOC_ID);
    }

    @Test
    public void testFindPatientById() throws Exception {
        findByIdPatient = Mockito.mock(Patient.class);
        when(findByIdPatient.getPatientId()).thenReturn(PATIENT_ID);
        when(patientDao.findPatientById(PATIENT_ID)).thenReturn(Optional.of(findByIdPatient));

        Patient patientFound = patientServiceImpl.findPatientById(PATIENT_ID);

        assertEquals(patientFound.getPatientId(), findByIdPatient.getPatientId());
    }

    @Test
    public void testFindPatiendByEmail() throws Exception {
        findByEmailPatient = Mockito.mock(Patient.class);
        when(patientDao.findPatientByEmail(PATIENT_EMAIL)).thenReturn(findByEmailPatient);
        when(findByEmailPatient.getEmail()).thenReturn(PATIENT_EMAIL);

        Patient patientFound = patientServiceImpl.findPatientByEmail(PATIENT_EMAIL);

        assertEquals(patientFound.getEmail(), findByEmailPatient.getEmail());
    }

    @Test
    public void testCreateToken() {
        createTokenPatient = Mockito.mock(Patient.class);
        createVerification = Mockito.mock(Verification.class);
        when(patientDao.createToken(createTokenPatient)).thenReturn(createVerification);

        Verification verificationGiven = patientServiceImpl.createToken(createTokenPatient);

        assertEquals(createVerification, verificationGiven);
    }

    @Test
    public void testFindVerification() {
        findVerification = Mockito.mock(Verification.class);
        when(patientDao.findToken(TOKEN)).thenReturn(Optional.of(findVerification));

        Optional<Verification> verificationFound = patientServiceImpl.findToken(TOKEN);

        assertEquals(findVerification, verificationFound.get());
    }

    @Test
    public void testEnableUser() {
        enablePatient = Mockito.mock(Patient.class);
        when(patientDao.enableUser(enablePatient)).thenReturn(enablePatient);

        patientServiceImpl.enableUser(enablePatient);
    }

    @Test
    public void testDeleteUser() {
        deletePatient = Mockito.mock(Patient.class);

        patientServiceImpl.deleteUser(deletePatient);
    }

    @Test
    public void testGetHistoricalAppointments() {
        historicalAppointmentPatient = Mockito.mock(Patient.class);
        appointment1 = Mockito.mock(Appointment.class);
        appointment2 = Mockito.mock(Appointment.class);
        List<Appointment> appointments = new LinkedList<>();
        appointments.add(0, appointment1);
        appointments.add(1, appointment2);
        when(patientDao.getHistoricalAppointments(historicalAppointmentPatient)).thenReturn(appointments);

        List<Appointment> appointmentsFound = patientServiceImpl.getHistoricalAppointments(historicalAppointmentPatient);

        assertEquals(appointments.size(), appointmentsFound.size());
        assertEquals(appointmentsFound.get(0), appointment1);
        assertEquals(appointmentsFound.get(1), appointment2);
    }

    @Test
    public void testGetFutureAppointments() {
        futureAppointmentPatient = Mockito.mock(Patient.class);
        futureAppointment1 = Mockito.mock(Appointment.class);
        futureAppointment2 = Mockito.mock(Appointment.class);
        List<Appointment> appointments = new LinkedList<>();
        appointments.add(0, futureAppointment1);
        appointments.add(1, futureAppointment2);
        when(patientDao.getFutureAppointments(futureAppointmentPatient)).thenReturn(appointments);

        List<Appointment> appointmentsFound = patientServiceImpl.getFutureAppointments(futureAppointmentPatient);

        assertEquals(appointments.size(), appointmentsFound.size());
        assertEquals(appointmentsFound.get(0), futureAppointment1);
        assertEquals(appointmentsFound.get(1), futureAppointment2);
    }

    @Test
    public void testGetFavoriteDoctors() {
        favoritePatient = Mockito.mock(Patient.class);
        favDoctor1 = Mockito.mock(Favorite.class);
        favDoctor2 = Mockito.mock(Favorite.class);
        List<Favorite> favs = new LinkedList<>();
        favs.add(0, favDoctor1);
        favs.add(1, favDoctor2);
        when(patientDao.getFavoriteDoctors(favoritePatient)).thenReturn(favs);

        List<Favorite> favsFound = patientServiceImpl.getFavoriteDoctors(favoritePatient);

        assertEquals(favs.size(), favsFound.size());
        assertEquals(favsFound.get(0), favDoctor1);
        assertEquals(favsFound.get(1), favDoctor2);
    }
}
