package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;

public class CancelAppointmentForm {

    Doctor doctor;
    Patient patient;
    String appointmentDay;
    String appointmentTime;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
}
