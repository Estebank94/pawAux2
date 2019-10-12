package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import java.net.URI;
import java.util.List;

public class InsuranceDTO {
    private Integer id;
    private String name;
    private InsurancePlanListDTO plans;

    public InsuranceDTO () {
    }

    public InsuranceDTO (Insurance insurance){
        this.id = insurance.getId();
        this.name = insurance.getName();
        this.plans = new InsurancePlanListDTO(insurance.getPlans());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InsurancePlanListDTO getPlans() {
        return plans;
    }

    public void setPlans(InsurancePlanListDTO plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "InsuranceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plans=" + plans +
                '}';
    }
}
