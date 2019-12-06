package ar.edu.itba.paw.webapp.dto.appointment;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.webapp.dto.doctor.BasicDoctorDTO;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorDTO;

public class PatientAppointmentDTO {
    private String appointmentDay;
    private String appointmentTime;
    private BasicDoctorDTO doctor;

    public PatientAppointmentDTO (){}

    public PatientAppointmentDTO (Appointment appointment){
        this.appointmentDay = appointment.getAppointmentDay();
        this.appointmentTime = appointment.getAppointmentTime();
        this.doctor = new BasicDoctorDTO(appointment.getDoctor());
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

    public BasicDoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(BasicDoctorDTO doctor) {
        this.doctor = doctor;
    }
}
