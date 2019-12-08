package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Insurance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by estebankramer on 10/11/2018.
 */
public interface InsuranceService {

    Insurance getInsuranceByName(String name);
}
