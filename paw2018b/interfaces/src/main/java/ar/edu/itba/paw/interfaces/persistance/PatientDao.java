package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;

import java.util.Optional;

public interface PatientDao {
    Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException;
    Boolean setDoctorId(Patient patient, Doctor doctor) throws NotFoundDoctorException, NotCreatePatientException;
    Optional<Patient> findPatientById(Integer id);
    Patient findPatientByEmail(String email);
}
