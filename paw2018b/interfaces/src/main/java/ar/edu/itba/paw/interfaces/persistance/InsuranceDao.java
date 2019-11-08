package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Insurance;

/**
 * Created by estebankramer on 17/10/2018.
 */
public interface InsuranceDao {
    Insurance createInsurance(String name);

    Insurance findInsuranceByName(String name);
}
