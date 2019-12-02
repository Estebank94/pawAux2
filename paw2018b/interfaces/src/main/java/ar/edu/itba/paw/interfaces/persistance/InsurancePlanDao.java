package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface InsurancePlanDao {

      List<InsurancePlan> getInsurancePlansFromAllInsurances(Set<Insurance> insurance);

      List<InsurancePlan> getInsurancePlans(Insurance insurance);

      InsurancePlan createInsurancePlan(Insurance insurance, String plan);

      InsurancePlan findInsurancePlanByPlanName(String name);

      List<InsurancePlan> findAllInsurancePlansByInsurance(Insurance insurance);

      List<InsurancePlan> getInsurancePlansByList (List<String> insurancePlanNames);

}
