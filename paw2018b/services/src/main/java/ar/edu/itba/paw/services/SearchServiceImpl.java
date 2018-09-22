package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.ListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Service("SearchDaoImpl")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public Optional<List<ListItem>> listInsurances() {
        return searchDao.listInsurances();
    }

    @Override
    public Optional<List<String>> listInsurancesWithDoctors(){
        return searchDao.listInsurancesWithDoctors();
    }

    @Override
    public Optional<List<ListItem>> listZones() {
        return null;
    }

}
