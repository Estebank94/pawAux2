package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.InsuranceDao;
import ar.edu.itba.paw.interfaces.InsuranceService;
import ar.edu.itba.paw.models.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by estebankramer on 10/11/2018.
 */
@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    InsuranceDao insuranceDao;


    @Override
    public Insurance getInsuranceByName(String name) {
        return insuranceDao.findInsuranceByName(name);
    }
}
