package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by estebankramer on 04/11/2018.
 */
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "doctorid")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;

    private Boolean favoriteCancelled;

    public Favorite(Doctor doctor, Patient patient, Boolean favoriteCancelled) {
        this.doctor = doctor;
        this.patient = patient;
        this.favoriteCancelled = favoriteCancelled;
    }

    public Favorite(){
    }

    public Favorite(Doctor doctor, Patient patient){
        this.doctor = doctor;
        this.patient = patient;
        this.favoriteCancelled = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Boolean getFavoriteCancelled() { return favoriteCancelled; }

    public void setFavoriteCancelled(Boolean favoriteCancelled) { this.favoriteCancelled = favoriteCancelled; }

    public void removeFavorite(){
        setFavoriteCancelled(true);
        return;
    }
}
