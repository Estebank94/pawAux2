package ar.edu.itba.paw.models;

import ar.edu.itba.paw.App;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Appointment implements Comparable<Appointment>{
    private Integer id;
    private LocalDate appointmentDay;
    private LocalTime appointmentTime;
    private Integer clientId;
    private String clientrole;

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.id = null;
        this.clientId = null;
        this.clientrole = null;
    }

    public Appointment(Integer id, LocalDate appointmentDay, LocalTime appointmentTime, Integer clientId, String clientrole) {
        this.id = id;
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.clientId = clientId;
        this.clientrole = clientrole;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAppointmentDay().toString())
                .append(" - ")
                .append(getAppointmentTime().toString());
        return sb.toString();
    }

    @Override
    public int compareTo(Appointment o) {
        Appointment appointment = (Appointment) o;
        int dayComparator = getAppointmentDay().compareTo(appointment.getAppointmentDay());
        int timeComparator = getAppointmentTime().compareTo(appointment.getAppointmentTime());
        if (dayComparator> 0){
            return 1;
        } else if(dayComparator < 0 ){
            return -1;
        }
        if (timeComparator > 0){
            return 1;
        } else if (timeComparator < 0){
            return -1;
        }
        return 0;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentDay, that.appointmentDay) &&
                Objects.equals(appointmentTime, that.appointmentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentDay, appointmentTime);
    }

}

