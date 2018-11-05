package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.FavoriteDao;
import ar.edu.itba.paw.interfaces.FavoriteService;
import ar.edu.itba.paw.models.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by estebankramer on 04/11/2018.
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteDao favoriteDao;

    @Override
    @Transactional
    public void addFavorite(Favorite favorite) {
        favoriteDao.addFavorite(favorite);
    }
}
