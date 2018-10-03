package ar.edu.itba.paw.models;

import ar.edu.itba.paw.App;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Appointment implements Comparable<Appointment>{
    private LocalDate appointmentDay;
    private LocalTime appointmentTime;
    private Integer clientId;
    private Integer doctorId;
    private String clientFirstName;
    private String clientLastName;
    private System doctorFirstName;
    private System doctorLastName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.clientId = null;
        this.doctorId = null;
    }

    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime, Integer clientId, String clientFirstName, String clientLastName,
                       String phoneNumber) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.clientId = clientId;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.phoneNumber = phoneNumber;
    }
    public Appointment(LocalDate appointmentDay, LocalTime appointmentTime, Integer doctorId, String doctorFirstName, String doctorLastName,
                       String phoneNumber, Integer clientId) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.doctorId = clientId;
        this.clientFirstName = doctorFirstName;
        this.clientLastName = doctorLastName;
        this.phoneNumber = phoneNumber;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }
}

