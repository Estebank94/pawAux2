package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Specialty;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SpecialtyListDTO {
    private List<String> specialties;

    public SpecialtyListDTO () {
    }

    public SpecialtyListDTO(List<Specialty> allSpecialties){
        this.specialties = new LinkedList<>();
        for (Specialty specialty : allSpecialties){
            this.specialties.add(specialty.getSpeciality());
        }
    }

    public SpecialtyListDTO(Set<Specialty> specialties){
        this.specialties = new LinkedList<>();
        for (Specialty specialty : specialties){
            this.specialties.add(specialty.getSpeciality());
        }
    }


    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }
}
