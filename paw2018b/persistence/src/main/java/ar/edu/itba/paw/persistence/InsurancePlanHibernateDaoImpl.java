package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.InsurancePlanDao;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

/**
 * Created by estebankramer on 17/10/2018.
 */
public class InsurancePlanHibernateDaoImpl implements InsurancePlanDao {

    @PersistenceContext
    private EntityManager em;

//    @Override
//    public List<InsurancePlan> getInsurancePlans(Set<String> insurance) {
//        final TypedQuery<InsurancePlan> query = em.createQuery("from InsurancePlan", InsurancePlan.class);
//        final List<InsurancePlan> list = query.getResultList();
//        if(!list.isEmpty()){
//            List<Integer>
//        }
//    }

    @Override
    public InsurancePlan createInsurancePlan(Insurance insurance, String plan) {
        final InsurancePlan insurancePlan = new InsurancePlan(insurance, plan);
        em.persist(insurancePlan);
        return insurancePlan;
    }

    private String parseInsurance(String insurance){

        char [] insuranceNameArray = insurance.toCharArray();
        StringBuilder string = new StringBuilder();
        for(int i=0; i<insuranceNameArray.length; i++){
            if(insuranceNameArray[i] != '.'){string.append(insuranceNameArray[i]);}
            else{string.append(' ');}
        }

        return string.toString();
    }
}
