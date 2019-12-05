package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import java.util.*;

public class InsuranceListDTO {
    private List<InsuranceDTO> insurances;

    public InsuranceListDTO(){
    }

    public InsuranceListDTO(List<Insurance> insuranceList){
        this.insurances = new LinkedList<>();
        for (Insurance insurance: insuranceList){
            this.insurances.add(new InsuranceDTO(insurance));
        }
    }

    public List<InsuranceDTO> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<InsuranceDTO> insurances) {
        this.insurances = insurances;
    }
}
