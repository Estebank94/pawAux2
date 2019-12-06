package ar.edu.itba.paw.webapp.dto.patient;

import ar.edu.itba.paw.interfaces.persistance.FavoriteDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.webapp.dto.FavoriteDoctorDTO;
import ar.edu.itba.paw.webapp.dto.appointment.PatientAppointmentDTO;

import java.util.ArrayList;
import java.util.List;

public class PatientPersonalInformationDTO {
    List<PatientAppointmentDTO> historicalAppointments;
    List<PatientAppointmentDTO> futureAppointments;
    List<FavoriteDoctorDTO> favorites;

    public PatientPersonalInformationDTO (){}

    public PatientPersonalInformationDTO(List<Appointment> historicalAppointments, List<Appointment> futureAppointments, List<Favorite> favorites) {
        this.historicalAppointments = new ArrayList<>();
        for (Appointment ap: historicalAppointments){
            this.historicalAppointments.add(new PatientAppointmentDTO(ap));
        }
        this.futureAppointments = new ArrayList<>();
        for (Appointment ap: futureAppointments){
            this.futureAppointments.add(new PatientAppointmentDTO(ap));
        }

        this.favorites = new ArrayList<>();
        for (Favorite fav: favorites){
            this.favorites.add(new FavoriteDoctorDTO(fav));
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

    public List<FavoriteDoctorDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteDoctorDTO> favorites) {
        this.favorites = favorites;
    }
}
