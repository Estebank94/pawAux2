package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Patient;

import java.util.LinkedList;
import java.util.List;

public class PatientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<PatientAppointmentDTO> appointments;
    private List<FavoriteDoctorDTO> favorites;

    public PatientDTO(){
    }

    public PatientDTO(Patient patient){
        id = patient.getId();
        firstName = patient.getFirstName();
        lastName = patient.getLastName();
        phoneNumber = patient.getPhoneNumber();
        email = patient.getEmail();
        password = patient.getEmail();

        this.appointments = new LinkedList<>();
        for (Appointment appointment: patient.getAppointments()){
            this.appointments.add(new PatientAppointmentDTO(appointment));
        }

        this.favorites = new LinkedList<>();
        for (Favorite favorite : patient.getFavorites()){
            this.favorites.add(new FavoriteDoctorDTO(favorite));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PatientAppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<PatientAppointmentDTO> appointments) {
        this.appointments = appointments;
    }

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
                ", password='" + password + '\'' +
                '}';
    }
}
