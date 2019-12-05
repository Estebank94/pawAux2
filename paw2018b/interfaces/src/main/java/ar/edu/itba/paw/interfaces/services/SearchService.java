package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;

import java.util.List;

/**
 * Created by estebankramer on 02/09/2018.
 */

public interface SearchService {
    List<Insurance> listInsurances();

    List<Specialty> listSpecialtiesWithDoctors();

    List<InsurancePlan> listInsuranceWithDoctors();

//    Optional<List<ListItem>> listInsurancesWithDoctors();

    List<Specialty> listSpecialties();

//    Optional<List<ListItem>> listSpecialtiesWithDoctors();

    List<InsurancePlan> listInsurancePlans();

    List<String> getFutureDays();
}
