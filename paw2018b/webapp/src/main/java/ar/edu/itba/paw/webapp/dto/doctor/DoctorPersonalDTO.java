package ar.edu.itba.paw.webapp.dto.doctor;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.webapp.dto.reviews.ReviewDTO;
import ar.edu.itba.paw.webapp.dto.appointment.DoctorAppointmentDTO;

import java.util.ArrayList;
import java.util.List;

public class DoctorPersonalDTO {
    List<DoctorAppointmentDTO> futureAppointments;
    List<DoctorAppointmentDTO> historicalAppointments;
    List<ReviewDTO> reviews;

    public DoctorPersonalDTO() {
    }

    public DoctorPersonalDTO(List<Appointment> futureAppointments, List<Appointment> historicalAppointments, List<Review> reviews) {
        this.futureAppointments = new ArrayList<>();
        for (Appointment ap: futureAppointments){
            this.futureAppointments.add(new DoctorAppointmentDTO(ap));
        }
        this.historicalAppointments = new ArrayList<>();
        for (Appointment ap: historicalAppointments){
            this.historicalAppointments.add(new DoctorAppointmentDTO(ap));
        }
        this.reviews = new ArrayList<>();
        for (Review rev: reviews){
            this.reviews.add(new ReviewDTO(rev));
        }
    }

    public List<DoctorAppointmentDTO> getFutureAppointments() {
        return futureAppointments;
    }

    public void setFutureAppointments(List<DoctorAppointmentDTO> futureAppointments) {
        this.futureAppointments = futureAppointments;
    }

    public List<DoctorAppointmentDTO> getHistoricalAppointments() {
        return historicalAppointments;
    }

    public void setHistoricalAppointments(List<DoctorAppointmentDTO> historicalAppointments) {
        this.historicalAppointments = historicalAppointments;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
