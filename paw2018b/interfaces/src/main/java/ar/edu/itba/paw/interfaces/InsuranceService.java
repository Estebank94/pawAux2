package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Insurance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by estebankramer on 10/11/2018.
 */
public interface InsuranceService {

    public Insurance getInsuranceByName(String name);
}
