package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;

import java.net.URI;
import java.util.List;

public class InsuranceDTO {
    private Integer id;
    private String name;
    private List<InsurancePlan> plans;
    private URI uri;

    public InsuranceDTO () {
    }

    public InsuranceDTO(Insurance insurance, URI baseURI){
        this.id = insurance.getId();
        this.name = insurance.getName();
        this.plans = insurance.getPlans();
        this.uri = baseURI.resolve(String.valueOf(this.id));
    }

    public InsuranceDTO (Insurance insurance){
        this.id = insurance.getId();
        this.name = insurance.getName();
        this.plans = insurance.getPlans();
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

    public List<InsurancePlan> getPlans() {
        return plans;
    }

    public void setPlans(List<InsurancePlan> plans) {
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
