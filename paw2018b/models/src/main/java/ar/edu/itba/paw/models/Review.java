package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Entity(name = "review")
public class Review {
    private Integer stars;
    private String dayTime;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="doctorid")
    private Doctor doctor;

    private String reviewerFirstName;
    private String reviewerLastName;

    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment")
    private Appointment appointment;

    public Review(){
    }

    public Review(Integer stars, String description, Doctor doctor, Patient patient, Appointment appointment){
        this.stars = stars;
        this.dayTime = LocalDate.now().toString();
        this.description = description;
        this.doctor = doctor;
        this.reviewerFirstName = patient.getLastName();
        this.reviewerLastName = patient.getFirstName();
        this.patient = patient;
        this.appointment = appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dateTime) {
        this.dayTime = dayTime;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public void setReviewerFirstName(String reviewerFirstName) {
        this.reviewerFirstName = reviewerFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public void setReviewerLastName(String reviewerLastName) {
        this.reviewerLastName = reviewerLastName;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
