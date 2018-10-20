package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MedicalCareDao;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.InsurancePlan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by estebankramer on 19/10/2018.
 */
public class MedicalCareHibernateDaoImpl implements MedicalCareDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addMedicalCare(Doctor doctor, InsurancePlan insurancePlan) {


    }

    @Override
    public void addMedicalCare(Doctor doctor, List<InsurancePlan> insurancePlans) {

    }
}
