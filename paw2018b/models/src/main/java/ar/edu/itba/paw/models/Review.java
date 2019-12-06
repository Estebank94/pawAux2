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

    public Review(){
    }

    public Review(Integer stars, String description, Doctor doctor, String reviewerFirstName, String reviewerLastName){
        this.stars = stars;
        this.dayTime = LocalDate.now().toString();
        this.description = description;
        this.doctor = doctor;
        this.reviewerFirstName = reviewerFirstName;
        this.reviewerLastName = reviewerLastName;
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

}
