package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.DescriptionDao;
import ar.edu.itba.paw.models.Description;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by estebankramer on 10/10/2018.
 */

@Repository
public class DescriptionHibernateDaoImpl implements DescriptionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Description createDescription(String certificate, String languages, String education) {
        final Description description = new Description(certificate, languages, education);
        em.persist(description);
        return description;
    }
}
