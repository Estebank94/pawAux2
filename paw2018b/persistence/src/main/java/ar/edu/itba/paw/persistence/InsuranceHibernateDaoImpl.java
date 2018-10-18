package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.InsuranceDao;
import ar.edu.itba.paw.models.Insurance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by estebankramer on 17/10/2018.
 */
public class InsuranceHibernateDaoImpl implements InsuranceDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Insurance createInsurance(String name) {
        final Insurance insurance = new Insurance(name);
        em.persist(insurance);
        return insurance;
    }
}
