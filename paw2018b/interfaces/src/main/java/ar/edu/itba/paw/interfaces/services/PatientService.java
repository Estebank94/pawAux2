package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    Patient findPatientById(Integer id) throws NotValidPatientIdException, NotCreatePatientException, NotFoundPacientException;

    Patient findPatientByEmail(String email) throws NotValidPatientIdException, NotCreatePatientException, NotFoundPacientException, NotValidEmailException;

    Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex) throws IllegalArgumentException, RepeatedEmailException, NotValidFirstNameException, NotValidLastNameException, NotValidPhoneNumberException, NotValidEmailException, NotValidPasswordException, NotCreatePatientException;

    Boolean setDoctorId(Patient patient, Doctor doctor) throws NotFoundDoctorException, NotValidPatientIdException, NotValidDoctorIdException, NotCreatePatientException;

    Verification createToken(Patient patient);

    Optional<Verification> findToken(final String token);

    void enableUser(final Patient patient);

    Patient confirmUser(final String token) throws VerificationNotFoundException;

    void deleteUser(final Patient patient);

    List<Appointment> getHistoricalAppointments(Patient patient);

    List<Appointment> getFutureAppointments (Patient patient);

    List<Favorite> getFavoriteDoctors(Patient patient);

    public boolean emailTaken(String email);
}
