package ar.edu.itba.paw.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

/**
 * Created by estebankramer on 17/10/2018.
 */

@Entity
@Table(name="insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="insurancename")
    private String name;

    @OneToMany(mappedBy = "insurance")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<InsurancePlan> plans;

    public Insurance(String name){
        this.name = name;
    }

    @Autowired
    public Insurance(){

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
}
