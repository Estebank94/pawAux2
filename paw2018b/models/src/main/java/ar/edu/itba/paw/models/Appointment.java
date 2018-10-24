package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="appointment")
public class Appointment implements Comparable<Appointment>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    private LocalDate appointmentDay;
//    private LocalTime appointmentTime;
    private String appointmentDay;
    private String appointmentTime;
    @ManyToOne
    @JoinColumn(name="clientid")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name="doctorid")
    private Doctor doctor;

    public Appointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Appointment(String appointmentDay, String appointmentTime) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
    }

    public Appointment(String appointmentDay, String appointmentTime, Patient patient) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
    }

    @Autowired
    public Appointment(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

