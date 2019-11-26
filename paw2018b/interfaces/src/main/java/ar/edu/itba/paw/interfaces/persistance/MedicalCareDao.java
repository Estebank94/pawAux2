package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.InsurancePlan;

import java.util.List;

public interface MedicalCareDao {

    void addMedicalCare(Doctor doctor, InsurancePlan insurancePlan);

    void addMedicalCare(Doctor doctor, List<InsurancePlan> insurancePlans);
}
