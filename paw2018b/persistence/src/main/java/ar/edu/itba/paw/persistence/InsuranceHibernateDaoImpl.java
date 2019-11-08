package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.InsuranceDao;
import ar.edu.itba.paw.models.Insurance;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by estebankramer on 17/10/2018.
 */

@Repository
public class InsuranceHibernateDaoImpl implements InsuranceDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Insurance createInsurance(String name) {
        final Insurance insurance = new Insurance(name);
        em.persist(insurance);
        return insurance;
    }

    @Override
    public Insurance findInsuranceByName(String name) {
        final TypedQuery<Insurance> query = em.createQuery("FROM Insurance " +
                "WHERE name = :name ", Insurance.class);
        query.setParameter("name", name);
        Insurance insurance = query.getSingleResult();
        System.out.println(insurance);
        return insurance;
    }
}
