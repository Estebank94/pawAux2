package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.InsurancePlanDao;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by estebankramer on 17/10/2018.
 */

@Repository
public class InsurancePlanHibernateDaoImpl implements InsurancePlanDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<InsurancePlan> getInsurancePlansFromAllInsurances(Set<Insurance> insurance) {
        List<InsurancePlan> list = new ArrayList<>();
        for(Insurance i : insurance){
            list.addAll(getInsurancePlans(i));
        }
        return (list.isEmpty() ?  null : list);
    }

    public List<InsurancePlan> getInsurancePlans(Insurance insurance) {
        final TypedQuery<InsurancePlan> query = em.createQuery("FROM InsurancePlan WHERE insuranceID = :insuranceID", InsurancePlan.class);
        query.setParameter("insuranceID", insurance.getId());
        final List<InsurancePlan> list = query.getResultList();
        return (list.isEmpty() ?  null : list);
    }

    @Override
    public InsurancePlan createInsurancePlan(Insurance insurance, String plan) {
        final InsurancePlan insurancePlan = new InsurancePlan(insurance, plan);
        em.persist(insurancePlan);
        return insurancePlan;
    }

    @Override
    public InsurancePlan findInsurancePlanByPlanName(String name) {
        final TypedQuery<InsurancePlan> query = em.createQuery("FROM InsurancePlan WHERE plan = :plan", InsurancePlan.class);
        query.setParameter("plan", name);
        return query.getSingleResult();
    }

    @Override
    public List<InsurancePlan> findAllInsurancePlansByInsurance(Insurance insurance) {
        final TypedQuery<InsurancePlan> query = em.createQuery("FROM InsurancePlan WHERE insurance = :insurance", InsurancePlan.class);
        query.setParameter("insurance", insurance);
        List<InsurancePlan> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<InsurancePlan> getInsurancePlansByList(List<String> insurancePlanNames) {
        final TypedQuery<InsurancePlan> query = em.createQuery("FROM InsurancePlan WHERE plan IN (:planNames)", InsurancePlan.class);
        query.setParameter("planNames", insurancePlanNames);
        List<InsurancePlan> list = query.getResultList();
        return list.isEmpty() ? Collections.EMPTY_LIST : list;
    }
}
