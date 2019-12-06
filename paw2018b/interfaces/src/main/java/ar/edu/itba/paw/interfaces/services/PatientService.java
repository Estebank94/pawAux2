package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    public Patient findPatientById(Integer id) throws NotValidPatientIdException, NotCreatePatientException, NotFoundPacientException;

    public Patient findPatientByEmail(String email);

    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex) throws IllegalArgumentException, RepeatedEmailException, NotValidFirstNameException, NotValidLastNameException, NotValidPhoneNumberException, NotValidEmailException, NotValidPasswordException, NotCreatePatientException;

    public Boolean setDoctorId(Patient patient, Doctor doctor) throws NotFoundDoctorException, NotValidPatientIdException, NotValidDoctorIdException, NotCreatePatientException;

    public Verification createToken(Patient patient);

    public Optional<Verification> findToken(final String token);

    public void enableUser(final Patient patient);

    public void deleteUser(final Patient patient);

    public List<Appointment> getHistoricalAppointments(Patient patient);

    public List<Appointment> getFutureAppointments (Patient patient);

    List<Favorite> getFavoriteDoctors(Patient patient);
}
