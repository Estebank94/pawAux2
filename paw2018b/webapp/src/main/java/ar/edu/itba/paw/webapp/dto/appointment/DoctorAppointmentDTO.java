package ar.edu.itba.paw.webapp.dto.appointment;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.webapp.dto.patient.BasicPatientDTO;

public class DoctorAppointmentDTO {
    private String appointmentDay;
    private String appointmentTime;

    private BasicPatientDTO patient;

    public DoctorAppointmentDTO() {
    }

    public DoctorAppointmentDTO (Appointment appointment){
        this.appointmentDay = appointment.getAppointmentDay();
        this.appointmentTime = appointment.getAppointmentTime();
        this.patient = new BasicPatientDTO(appointment.getPatient());
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
