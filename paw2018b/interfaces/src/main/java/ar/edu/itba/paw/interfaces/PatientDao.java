package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;

import java.util.Optional;

public interface PatientDao {
    Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException;
    Boolean setDoctorId(Integer patientId, Integer doctorId) throws NotFoundDoctorException, NotCreatePatientException;
    Optional<Patient> findPatientById(Integer id);
    Optional<Patient> findPatientByEmail(String email);
}
