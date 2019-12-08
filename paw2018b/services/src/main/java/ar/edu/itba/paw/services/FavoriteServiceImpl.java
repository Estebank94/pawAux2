package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.FavoriteDao;
import ar.edu.itba.paw.interfaces.services.FavoriteService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.exceptions.NotCreatedFavoriteException;
import ar.edu.itba.paw.models.exceptions.NotRemoveFavoriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by estebankramer on 04/11/2018.
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    @Transactional(rollbackFor = SQLException.class)
    @Override
    public void addFavorite(Doctor doctor, Patient patient) throws NotCreatedFavoriteException {
        LOGGER.debug("FavoriteServiceImpl: addFavorite");

        Favorite favorite = null;
        Optional<Favorite> fav = Optional.empty();

        try{
            fav = favoriteDao.findFavorite(doctor, patient);
        } catch (Exception e){
            LOGGER.debug("No favorite doctor");
        }

        if (fav.isPresent()){
            favoriteDao.undoRemoveFavorite(fav.get());
            return;
        }
        try{
            favoriteDao.addFavorite(doctor, patient);
        } catch (Exception e){
            throw new NotCreatedFavoriteException();
        }

        return;
    }

    @Transactional(rollbackFor = SQLException.class)
    @Override
    public void removeFavorite(Doctor doctor, Patient patient) throws NotRemoveFavoriteException {
        Optional<Favorite> favorite;
        Favorite fav = null;
        try {
            favorite = favoriteDao.findFavorite(doctor, patient);
        } catch (NoResultException e){
            return;
        } catch (Exception e){
            throw new NotRemoveFavoriteException();
        }

        if (favorite.isPresent()){
            try {
               favoriteDao.removeFavorite(favorite.get());
            } catch (Exception e){
                return;
            }
        }
        return;
    }
}
