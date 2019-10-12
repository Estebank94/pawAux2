package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.InsurancePlan;

import java.util.LinkedList;
import java.util.List;

public class InsurancePlanListDTO {
    private List<InsurancePlanDTO> insurancePlanDTOList;

    public InsurancePlanListDTO (){}

    public InsurancePlanListDTO(List<InsurancePlan> insurancePlans){
        this.insurancePlanDTOList = new LinkedList<>();
        for (InsurancePlan insurancePlan: insurancePlans){
            this.insurancePlanDTOList.add(new InsurancePlanDTO(insurancePlan));
        }
    }

    public List<InsurancePlanDTO> getInsurancePlanDTOList() {
        return insurancePlanDTOList;
    }

    public void setInsurancePlanDTOList(List<InsurancePlanDTO> insurancePlanDTOList) {
        this.insurancePlanDTOList = insurancePlanDTOList;
    }
}
