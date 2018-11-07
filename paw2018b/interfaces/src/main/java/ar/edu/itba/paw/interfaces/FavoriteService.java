package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;

/**
 * Created by estebankramer on 04/11/2018.
 */
public interface FavoriteService {
    public void addFavorite(Doctor doctor, Patient patient);

    public void removeFavorite(Doctor doctor, Patient patient);
}
