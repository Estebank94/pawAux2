package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Patient;

import java.util.Optional;

/**
 * Created by estebankramer on 04/11/2018.
 */
public interface FavoriteDao {
    void addFavorite(Doctor doctor, Patient patient);

    void removeFavorite(Favorite favorite) throws Exception;

    Optional<Favorite> findFavorite (Doctor doctor, Patient patient) throws  Exception;

    public void undoRemoveFavorite(Favorite favorite);
}
