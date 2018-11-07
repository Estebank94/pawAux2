package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.interfaces.FavoriteService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Favorite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.itba.paw.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * Created by estebankramer on 04/11/2018.
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteDao favoriteDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    @Override
    @Transactional
    public void addFavorite(Doctor doctor, Patient patient) {
        LOGGER.debug("FavoriteServiceImpl: addFavorite");

        Favorite favorite = null;
        Optional<Favorite> fav = Optional.empty();

        try{
            fav = favoriteDao.findFavorite(doctor, patient);
        } catch (NoResultException e){

        } catch (Exception e){

        }

        if (fav.isPresent()){
            favoriteDao.undoRemoveFavorite(fav.get());
            return;
        }
        try{
            favoriteDao.addFavorite(doctor, patient);
        } catch (Exception e){
            throw e;
        }

        return;
    }

    @Override
    @Transactional
    public void removeFavorite(Doctor doctor, Patient patient){
        Optional<Favorite> favorite;

        try {
            favorite = favoriteDao.findFavorite(doctor, patient);
        } catch (NoResultException e){
            return;
        } catch (Exception e){
            return;
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
