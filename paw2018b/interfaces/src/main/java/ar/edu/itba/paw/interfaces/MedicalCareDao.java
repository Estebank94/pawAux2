package ar.edu.itba.paw.interfaces;

import java.util.List;

public interface MedicalCareDao {

    void addMedicalCare(Integer doctorId, Integer insurancePlanId);

    void addMedicalCare(Integer doctorId, List<Integer> insurancePlanId);
}
