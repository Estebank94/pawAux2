package ar.edu.itba.paw.models;

import javax.persistence.*;

/**
 * Created by estebankramer on 17/10/2018.
 */

@Entity
@Table(name="insurance")
public class Insurance {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="insurancename")
    private String name;

    public Insurance(String name){
        this.name = name;
    }

    public Insurance(){

    }
}
