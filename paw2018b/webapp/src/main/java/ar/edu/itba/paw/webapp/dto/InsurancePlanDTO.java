package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.InsurancePlan;

public class InsurancePlanDTO {
    private Integer id;
    private String plan;

    public InsurancePlanDTO (){}

    public InsurancePlanDTO(InsurancePlan insurancePlan){
        this.id = insurancePlan.getId();
        this.plan = insurancePlan.getPlan();
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
}
