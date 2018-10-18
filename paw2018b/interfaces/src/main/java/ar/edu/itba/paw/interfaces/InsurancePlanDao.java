package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface InsurancePlanDao {

//      List<Integer> getInsurancesPlanIds(Set<String> insurance);

      InsurancePlan createInsurancePlan(Insurance insurance, String plan);
}
