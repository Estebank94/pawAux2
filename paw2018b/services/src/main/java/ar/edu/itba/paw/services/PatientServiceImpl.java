package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws IllegalArgumentException {
        if(patientDao.findPatientByEmail(email).isPresent()){
            throw new IllegalArgumentException("Username with email " + email + " already exists");
        }
        return patientDao.createPatient(firstName, lastName, phoneNumber, email, password);
    }

    @Override
    public Boolean setDoctorId(Integer patientId, Integer doctorId) {
        return patientDao.setDoctorId(patientId, doctorId);
    }

    @Override
    public Patient findPatientById(Integer id) {
        return patientDao.findPatientById(id).get();
    }

    @Override
    public Patient findPatientByEmail(String email) {

        Optional<Patient> foundPatient = patientDao.findPatientByEmail(email);

        if(foundPatient.isPresent()){
            return patientDao.findPatientByEmail(email).get();
        }

        return null;
        
    }

}
