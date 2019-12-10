package ar.edu.itba.paw.models;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Entity
@Table(name="appointment")
@DynamicUpdate
public class Appointment implements Comparable<Appointment>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String appointmentDay;
    private String appointmentTime;

    private Boolean appointmentCancelled;

    @ManyToOne
    @JoinColumn(name="clientid")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="doctorid")
    private Doctor doctor;

    @OneToOne(mappedBy = "appointment")
    private Review review;

    public Appointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentCancelled = Boolean.FALSE;
    }

    public Appointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor, Boolean appointmentCancelled) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentCancelled = appointmentCancelled;
    }

    public Appointment(String appointmentDay, String appointmentTime) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.appointmentCancelled = Boolean.FALSE;
    }

    public Appointment(String appointmentDay, String appointmentTime, Patient patient) {
        this.appointmentDay = appointmentDay;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.appointmentCancelled = Boolean.FALSE;
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

    public Boolean getAppointmentCancelled() {
        return appointmentCancelled;
    }

    public void setAppointmentCancelled(Boolean appointmentCancelled) {
        this.appointmentCancelled = appointmentCancelled;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void cancelAppointment(){
        setAppointmentCancelled(true);
    }

    public boolean canCancel(){
        LocalDate appDate = LocalDate.parse(getAppointmentDay());
        LocalDateTime appDayTime = appDate.atTime(LocalTime.parse(getAppointmentTime()));
        if (appDayTime.isAfter(LocalDateTime.now().plusDays(2))){
            return true;
        } else {
            return false;
        }
    }
}

