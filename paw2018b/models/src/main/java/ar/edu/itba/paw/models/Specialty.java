package ar.edu.itba.paw.models;

import javax.persistence.*;

/**
 * Created by estebankramer on 19/10/2018.
 */
@Entity
@Table(name="speciality")
public class Specialty {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="specialtyname")
    private String speciality;

    public Specialty(Integer id, String speciality) {
        this.id = id;
        this.speciality = speciality;
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
