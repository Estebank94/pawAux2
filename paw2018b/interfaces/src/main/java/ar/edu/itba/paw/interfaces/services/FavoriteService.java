package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.FavoriteExistsException;
import ar.edu.itba.paw.models.exceptions.NotCreatedFavoriteException;
import ar.edu.itba.paw.models.exceptions.NotRemoveFavoriteException;


public interface FavoriteService {

    void addFavorite(Doctor doctor, Patient patient) throws NotCreatedFavoriteException, FavoriteExistsException;

    void removeFavorite(Doctor doctor, Patient patient) throws NotRemoveFavoriteException;
}
