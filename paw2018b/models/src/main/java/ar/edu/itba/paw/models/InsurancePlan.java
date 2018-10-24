package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by estebankramer on 17/10/2018.
 */

@Entity
@Table(name="insurancePlan")
public class InsurancePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="insuranceid")
    private Insurance insurance;

    @Column(name="insuranceplanname")
    private String plan;

    public InsurancePlan(Insurance insurance, String plan){
        this.insurance = insurance;
        this.plan = plan;
    }

    @Autowired
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
