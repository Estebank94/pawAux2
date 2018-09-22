package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MedicalCareDao;
import ar.edu.itba.paw.interfaces.MedicalCareService;
import org.springframework.beans.factory.annotation.Autowired;

public class MedicalCareServiceImpl implements MedicalCareService {

    @Autowired
    private MedicalCareDao medicalCareDao;


    @Override
    void addMedicalCare(Integer doctorId, Integer insurancePlanId){
        medicalCareDao.addMedicalCare(doctorId,insurancePlanId);
    }
}
