package ar.edu.itba.paw.webapp.dto.insurance;

import ar.edu.itba.paw.models.InsurancePlan;

public class InsurancePlanDTO {
    private Integer id;
    private String plan;
    private String insurance;

    public InsurancePlanDTO (){}

    public InsurancePlanDTO(InsurancePlan insurancePlan){
        this.id = insurancePlan.getId();
        this.plan = insurancePlan.getPlan();
        insurancePlan.getInsurance();
        this.insurance = insurancePlan.getInsurance().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }
}
