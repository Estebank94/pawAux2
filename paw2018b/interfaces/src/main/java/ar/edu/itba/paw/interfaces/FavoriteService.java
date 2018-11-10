package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatedFavoriteException;
import ar.edu.itba.paw.models.exceptions.NotRemoveFavoriteException;

/**
 * Created by estebankramer on 04/11/2018.
 */
public interface FavoriteService {
    public void addFavorite(Doctor doctor, Patient patient) throws NotCreatedFavoriteException;

    public void removeFavorite(Doctor doctor, Patient patient) throws NotRemoveFavoriteException;
}
