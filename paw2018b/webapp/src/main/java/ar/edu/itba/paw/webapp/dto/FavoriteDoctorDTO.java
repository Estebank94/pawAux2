package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.webapp.dto.doctor.BasicDoctorDTO;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorDTO;

public class FavoriteDoctorDTO {
    private BasicDoctorDTO doctor;

    public FavoriteDoctorDTO (){}

    public FavoriteDoctorDTO (Favorite favorite){
        this.doctor = new BasicDoctorDTO(favorite.getDoctor());
    }

    public BasicDoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(BasicDoctorDTO doctor) {
        this.doctor = doctor;
    }

}
