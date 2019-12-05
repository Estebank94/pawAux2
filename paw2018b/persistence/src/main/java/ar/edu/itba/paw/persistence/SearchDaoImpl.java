package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.SearchDao;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Repository
public class SearchDaoImpl implements SearchDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Insurance> listInsurances() {

        final TypedQuery<Insurance> query = em.createQuery("FROM Insurance", Insurance.class);

        final List<Insurance> list = query.getResultList();

        return list.isEmpty() ? Collections.emptyList() : list;
    }
    
    @Override
    public List<Specialty> listSpecialties() {

        final TypedQuery<Specialty> query = em.createQuery("FROM Specialty", Specialty.class);
        final List<Specialty> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;

    }

    @Override
    public List<Specialty> listSpecialtiesWithDoctors() {
        final TypedQuery<Specialty> query = em.createQuery("select distinct s from Doctor d join d.specialties s", Specialty.class);
        final List<Specialty> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<InsurancePlan> listInsuranceWithDoctors() {
        final TypedQuery<InsurancePlan> query = em.createQuery("select distinct s from Doctor d join d.insurancePlans s", InsurancePlan.class);
        final List<InsurancePlan> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<InsurancePlan> listInsurancePlans() {
        final TypedQuery<InsurancePlan> query = em.createQuery("FROM InsurancePlan", InsurancePlan.class);
        final List<InsurancePlan> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

}