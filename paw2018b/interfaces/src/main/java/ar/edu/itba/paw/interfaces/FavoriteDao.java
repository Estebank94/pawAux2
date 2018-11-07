package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Patient;

/**
 * Created by estebankramer on 04/11/2018.
 */
public interface FavoriteDao {
    void addFavorite(Doctor doctor, Patient patient);
}
