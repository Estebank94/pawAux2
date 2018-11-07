package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by estebankramer on 04/11/2018.
 */
@Repository
public class FavoriteHibernateDaoImpl implements FavoriteDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void addFavorite(Doctor doctor, Patient patient) {
        Favorite favorite = new Favorite(doctor, patient);
        em.persist(favorite);
    }
}
