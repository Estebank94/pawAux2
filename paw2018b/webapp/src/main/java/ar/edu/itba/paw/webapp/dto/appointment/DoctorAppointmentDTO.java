package ar.edu.itba.paw.webapp.dto.appointment;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.webapp.dto.patient.BasicPatientDTO;
import ar.edu.itba.paw.webapp.dto.reviews.BasicReviewDTO;

public class DoctorAppointmentDTO {
    private String appointmentDay;
    private String appointmentTime;
    private BasicPatientDTO patient;
    private BasicReviewDTO review;
    private Integer id;

    public DoctorAppointmentDTO() {
    }

    public DoctorAppointmentDTO (Appointment appointment){
        this.appointmentDay = appointment.getAppointmentDay();
        this.appointmentTime = appointment.getAppointmentTime();
        this.patient = new BasicPatientDTO(appointment.getPatient());
        if (appointment.getReview() != null){
            review = new BasicReviewDTO(appointment.getReview());
        }
        this.id = appointment.getId();
    }

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public BasicPatientDTO getPatient() {
        return patient;
    }

    public void setPatient(BasicPatientDTO patient) {
        this.patient = patient;
    }

    public BasicReviewDTO getReview() {
        return review;
    }

    public void setReview(BasicReviewDTO review) {
        this.review = review;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
