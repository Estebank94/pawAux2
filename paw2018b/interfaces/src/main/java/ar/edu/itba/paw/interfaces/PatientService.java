package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotValidFirstNameException;
import ar.edu.itba.paw.models.exceptions.NotValidLastNameException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;

public interface PatientService {

    public Patient findPatientById(Integer id);

    public Patient findPatientByEmail(String email);

    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex) throws IllegalArgumentException, RepeatedEmailException, NotValidFirstNameException, NotValidLastNameException;

    public Boolean setDoctorId(Integer patientId, Integer doctorId);

}
