package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.*;

import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;

import java.util.List;
import java.util.Optional;

public interface PatientDao {
    Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException;
    Boolean setDoctorId(Patient patient, Doctor doctor) throws NotFoundDoctorException, NotCreatePatientException;
    Optional<Patient> findPatientById(Integer id);
    Patient findPatientByEmail(String email);
    Verification createToken(Patient patient);
    public Optional<Verification> findToken(String token);
    public void deleteToken(Verification token);
    public Patient enableUser(Patient patient);
    public void deleteUser(Patient patient);
    List<Appointment> getFutureAppointments(Patient patient);
    List<Appointment> getHistoricalAppointments(Patient patient);
    List<Favorite> getFavoriteDoctors(Patient patient);
}
