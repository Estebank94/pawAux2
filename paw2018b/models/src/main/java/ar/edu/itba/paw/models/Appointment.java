package ar.edu.itba.paw.models;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Appointment implements Comparable<Appointment>{
    private LocalDate appointmentDay;
    private LocalTime appointmentTime;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = null;
        this.doctor = null;
    }

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime, Patient patient) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = null;
    }

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime, Patient patient, Doctor doctor) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

