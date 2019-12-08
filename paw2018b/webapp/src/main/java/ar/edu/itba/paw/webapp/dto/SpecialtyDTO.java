package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Specialty;

import java.net.URI;

public class SpecialtyDTO {

    // private Integer id;
    private String speciality;
    private URI uri;

    public SpecialtyDTO () {}

    public SpecialtyDTO (Specialty specialty) {
        this.speciality = specialty.getSpeciality();
    }

    public SpecialtyDTO (Specialty specialty, URI baseUri){
        this.speciality = specialty.getSpeciality();
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "SpecialtyDTO{" +
                ", speciality='" + speciality + '\'' +
                ", uri=" + uri +
                '}';
    }
}

