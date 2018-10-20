package ar.edu.itba.paw.models;

import javax.persistence.*;

/**
 * Created by estebankramer on 17/10/2018.
 */

@Entity
@Table(name="insurancePlan")
public class InsurancePlan {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Insurance insurance;
    private String plan;

    public InsurancePlan(Insurance insurance, String plan){
        this.insurance = insurance;
        this.plan = plan;
    }

    public InsurancePlan(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
