package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Specialty;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SpecialtyListDTO {
    private List<SpecialtyDTO> specialties;

    public SpecialtyListDTO () {
    }

    public SpecialtyListDTO(List<Specialty> allSpecialties){
        this.specialties = new LinkedList<>();
        for (Specialty specialty : allSpecialties){
            this.specialties.add(new SpecialtyDTO(specialty));
        }
    }

    public SpecialtyListDTO(Set<Specialty> specialties){
        this.specialties = new LinkedList<>();
        for (Specialty specialty : specialties){
            this.specialties.add(new SpecialtyDTO(specialty));
        }
    }


    public List<SpecialtyDTO> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<SpecialtyDTO> specialties) {
        this.specialties = specialties;
    }
}