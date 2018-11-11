package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.ListItem;
import ar.edu.itba.paw.models.Specialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Service("SearchDaoImpl")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public List<Insurance> listInsurances() {
        return searchDao.listInsurances();
    }

//    @Override
//    public List<ListItem>> listInsurancesWithDoctors() {
//        return searchDao.listInsurancesWithDoctors();
//    }

//    @Transactional
//    @Override
//    public Optional<List<ListItem>> listSpecialtiesWithDoctors() {
//        return searchDao.listSpecialtiesWithDoctors();
//    }
//
    @Override
    public List<Specialty> listSpecialties() {
        return searchDao.listSpecialties();
    }

    @Override
    public List<InsurancePlan> listInsurancePlans() {
        return searchDao.listInsurancePlans();
    }

    @Override
    public List<String> getFutureDays() {
        List<String> ret = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String day = null;
        for (int i = 0; i < 7; i++){
            day = new String (today.plusDays(i).toString());
            ret.add(day);
        }
        return ret;
    }

}
