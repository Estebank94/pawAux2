package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.*;

public interface PatientService {

    public Patient findPatientById(Integer id);

    public Patient findPatientByEmail(String email);

    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex) throws IllegalArgumentException, RepeatedEmailException, NotValidFirstNameException, NotValidLastNameException, NotValidPhoneNumberException, NotValidEmailException, NotValidPasswordException, NotCreatePatientException;

    public Boolean setDoctorId(Integer patientId, Integer doctorId);

}
