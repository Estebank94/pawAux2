package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.SearchDao;
import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Service("SearchDaoImpl")
public class SearchServiceImpl implements SearchService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Autowired
    private SearchDao searchDao;

    @Override
    public List<Insurance> listInsurances() {
        return searchDao.listInsurances();
    }

    @Override
    public List<Specialty> listSpecialties() {
        return searchDao.listSpecialties();
    }

    @Override
    public List<InsurancePlan> listInsuranceWithDoctors() {
        return searchDao.listInsuranceWithDoctors();
    }

    @Override
    public List<Specialty> listSpecialtiesWithDoctors() {
        return searchDao.listSpecialtiesWithDoctors();
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
