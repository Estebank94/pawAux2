package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String address, String sex) {
        return patientDao.createPatient(firstName, lastName, phoneNumber, address, sex );
    }

}
