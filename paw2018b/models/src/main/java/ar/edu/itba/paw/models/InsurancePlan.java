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
}
