package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.models.Favorite;
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
    public void addFavorite(Favorite favorite) {
        em.persist(favorite);
    }
}
