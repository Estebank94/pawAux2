package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.webapp.dto.doctor.BasicDoctorDTO;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorDTO;

public class FavoriteDoctorDTO {
    private int id;
    private BasicDoctorDTO doctor;
    private boolean favoriteCancelled;

    public FavoriteDoctorDTO (){}

    public FavoriteDoctorDTO (Favorite favorite){
        this.id = favorite.getId();
        this.doctor = new BasicDoctorDTO(favorite.getDoctor());
        this.favoriteCancelled = favorite.getFavoriteCancelled();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BasicDoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(BasicDoctorDTO doctor) {
        this.doctor = doctor;
    }

    public boolean isFavoriteCancelled() {
        return favoriteCancelled;
    }

    public void setFavoriteCancelled(boolean favoriteCancelled) {
        this.favoriteCancelled = favoriteCancelled;
    }
}
