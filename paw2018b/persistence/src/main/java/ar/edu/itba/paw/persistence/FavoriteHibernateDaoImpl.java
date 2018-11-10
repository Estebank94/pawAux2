package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

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

    @Override
    public void removeFavorite(Favorite favorite) throws Exception{
        favorite.removeFavorite();
        em.persist(favorite);
    }

    @Override
    public Optional<Favorite> findFavorite (Doctor doctor, Patient patient) throws  Exception{
        final TypedQuery<Favorite> query = em.createQuery("from Favorite as f WHERE f.doctor = :doctor AND f.patient = :patient", Favorite.class);
        query.setParameter("patient", patient);
        query.setParameter("doctor", doctor);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void undoRemoveFavorite(Favorite favorite){
        favorite.setFavoriteCancelled(false);
        em.persist(favorite);
    }

}
