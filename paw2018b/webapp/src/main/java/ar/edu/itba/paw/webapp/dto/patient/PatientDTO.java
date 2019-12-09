package ar.edu.itba.paw.webapp.dto.patient;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.webapp.dto.FavoriteDoctorDTO;
import ar.edu.itba.paw.webapp.dto.appointment.PatientAppointmentDTO;
import ar.edu.itba.paw.webapp.dto.doctor.BasicDoctorDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PatientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private URI uri;
    private BasicDoctorDTO doctor;
    private String password;
    private List<PatientAppointmentDTO> appointments;
    private List<FavoriteDoctorDTO> favorites;

    public PatientDTO(){
    }

    public PatientDTO(Patient patient, URI baseUri) {
        id = patient.getId();
        firstName = patient.getFirstName();
        lastName = patient.getLastName();
        phoneNumber = patient.getPhoneNumber();
        email = patient.getEmail();
        if(patient.getDoctor()!=null){
            doctor = new BasicDoctorDTO(patient.getDoctor());
        }
        this.uri = baseUri.resolve(String.valueOf(this.id));
        this.favorites = new LinkedList<>();
        if(!favorites.isEmpty()) {
            for (Favorite favorite : patient.getFavorites()) {
                this.favorites.add(new FavoriteDoctorDTO(favorite));
            }
        }
    }


    public PatientDTO(Patient patient){
        id = patient.getId();
        firstName = patient.getFirstName();
        lastName = patient.getLastName();
        phoneNumber = patient.getPhoneNumber();
        email = patient.getEmail();
        if (patient.getDoctor() != null){
            doctor = new BasicDoctorDTO(patient.getDoctor());
        }
        this.favorites = new ArrayList<>();
        for (Favorite favorite : patient.getFavorites()){
            if (!favorite.getFavoriteCancelled()){
                this.favorites.add(new FavoriteDoctorDTO(favorite));
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

//    public BasicDoctorDTO getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(BasicDoctorDTO doctor) {
//        this.doctor = doctor;
//    }

    public List<FavoriteDoctorDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteDoctorDTO> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
