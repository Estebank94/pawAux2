package ar.edu.itba.paw.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Integer id;
    private LocalDate appointmentDay;
    private LocalTime appointmentTime;

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(LocalDate appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
