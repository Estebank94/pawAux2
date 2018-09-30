package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;

import java.util.Optional;

public interface PatientDao {
    Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password);
    Boolean setDoctorId(Integer patientId, Integer doctorId);
    Optional<Patient> findPatientById(Integer id);
    Optional<Patient> findPatientByEmail(String email);
}
