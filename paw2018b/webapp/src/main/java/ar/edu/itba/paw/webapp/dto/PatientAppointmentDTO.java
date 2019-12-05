package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;

public class PatientAppointmentDTO {
    private String appointmentDay;
    private String appointmentTime;
    private Boolean appointmentCancelled;
    private DoctorDTO doctor;

    public PatientAppointmentDTO (){}

    public PatientAppointmentDTO (Appointment appointment){
        this.appointmentDay = appointment.getAppointmentDay();
        this.appointmentTime = appointment.getAppointmentTime();
        this.appointmentCancelled = appointment.getAppointmentCancelled();
        this.doctor = new DoctorDTO(appointment.getDoctor());
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

    public Boolean getAppointmentCancelled() {
        return appointmentCancelled;
    }

    public void setAppointmentCancelled(Boolean appointmentCancelled) {
        this.appointmentCancelled = appointmentCancelled;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }
}
