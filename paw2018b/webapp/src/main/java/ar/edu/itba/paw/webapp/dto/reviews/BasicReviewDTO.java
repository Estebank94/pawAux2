package ar.edu.itba.paw.webapp.dto.reviews;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.webapp.dto.doctor.BasicDoctorDTO;
import ar.edu.itba.paw.webapp.dto.patient.BasicPatientDTO;

public class BasicReviewDTO {
    private Integer stars;
    private String dayTime;
    private String description;
    private BasicDoctorDTO doctor;
    private BasicPatientDTO patient;

    public BasicReviewDTO() {
    }

    public BasicReviewDTO(Review review) {
        if (review != null){
            this.stars = review.getStars();
            this.dayTime = review.getDayTime();
            this.description = review.getDescription();
            this.doctor = new BasicDoctorDTO(review.getDoctor());
            this.patient = new BasicPatientDTO(review.getPatient());
        }
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

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BasicDoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(BasicDoctorDTO doctor) {
        this.doctor = doctor;
    }

    public BasicPatientDTO getPatient() {
        return patient;
    }

    public void setPatient(BasicPatientDTO patient) {
        this.patient = patient;
    }
}
