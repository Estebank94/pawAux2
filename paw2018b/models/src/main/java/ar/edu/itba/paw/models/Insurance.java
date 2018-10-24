package ar.edu.itba.paw.models;

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
    private List<InsurancePlan> plans;

    public Insurance(String name){
        this.name = name;
    }

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
}
