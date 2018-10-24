package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by estebankramer on 19/10/2018.
 */
@Entity
@Table(name="specialty")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="specialtyname")
    private String speciality;

    public Specialty(Integer id, String speciality) {
        this.id = id;
        this.speciality = speciality;
    }

    @Autowired
    public Specialty(){

    }

    public Specialty(String speciality) {
        this.speciality = speciality;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
