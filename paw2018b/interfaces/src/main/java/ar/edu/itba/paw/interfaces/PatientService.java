package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;

public interface PatientService {

    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex);

}
