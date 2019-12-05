package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class PatientPersonalInformationDTO {
    List<PatientAppointmentDTO> historicalAppointments;
    List<PatientAppointmentDTO> futureAppointments;

    public PatientPersonalInformationDTO (){}

    public PatientPersonalInformationDTO(List<Appointment> historicalAppointments, List<Appointment> futureAppointments) {
        this.historicalAppointments = new ArrayList<>();
        for (Appointment ap: historicalAppointments){
            this.historicalAppointments.add(new PatientAppointmentDTO(ap));
        }
        this.futureAppointments = new ArrayList<>();
        for (Appointment ap: futureAppointments){
            this.futureAppointments.add(new PatientAppointmentDTO(ap));
        }
    }

    public List<PatientAppointmentDTO> getHistoricalAppointments() {
        return historicalAppointments;
    }

    public void setHistoricalAppointments(List<PatientAppointmentDTO> historicalAppointments) {
        this.historicalAppointments = historicalAppointments;
    }

    public List<PatientAppointmentDTO> getFutureAppointments() {
        return futureAppointments;
    }

    public void setFutureAppointments(List<PatientAppointmentDTO> futureAppointments) {
        this.futureAppointments = futureAppointments;
    }
}
