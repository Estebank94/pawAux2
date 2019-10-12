package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Favorite;

public class FavoriteDoctorDTO {
    private int id;
    private DoctorDTO doctor;
    private boolean favoriteCancelled;

    public FavoriteDoctorDTO (){}
    public FavoriteDoctorDTO (Favorite favorite){
        this.id = favorite.getId();
        this.doctor = new DoctorDTO(favorite.getDoctor());
        this.favoriteCancelled = favorite.getFavoriteCancelled();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public boolean isFavoriteCancelled() {
        return favoriteCancelled;
    }

    public void setFavoriteCancelled(boolean favoriteCancelled) {
        this.favoriteCancelled = favoriteCancelled;
    }
}
