package ar.edu.itba.paw.webapp.dto.insurance;

import ar.edu.itba.paw.models.InsurancePlan;

import java.util.LinkedList;
import java.util.List;

public class InsurancePlanListDTO {
    private List<InsurancePlanDTO> insurancePlans;

    public InsurancePlanListDTO (){}

    public InsurancePlanListDTO(List<InsurancePlan> insurancePlans){
        this.insurancePlans = new LinkedList<>();
        for (InsurancePlan insurancePlan: insurancePlans){
            this.insurancePlans.add(new InsurancePlanDTO(insurancePlan));
        }
    }

    public List<InsurancePlanDTO> getInsurancePlans() {
        return insurancePlans;
    }

    public void setInsurancePlans(List<InsurancePlanDTO> insurancePlans) {
        this.insurancePlans = insurancePlans;
    }
}
