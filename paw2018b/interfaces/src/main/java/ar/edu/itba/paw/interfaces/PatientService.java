package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;

public interface PatientService {

    public Patient findPatientById(Integer id);

    public Patient findPatientByEmail(String email);

    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex);

    public Boolean setDoctorId(Integer patientId, Integer doctorId);

}
