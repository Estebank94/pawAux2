package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;

public interface PatientDao {
    Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex);
}
