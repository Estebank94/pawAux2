package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Patient;

import java.net.URI;
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
    // List<Favorite> favorites;
    // private URI uri;

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
        // appointments = patient.getAppointments();
        // favorites = patient.getFavorites();
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

    /*
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
    */

    // public List<Favorite> getFavorites() {
    //     return favorites;
    // }

    /*
    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
    */

    public List<PatientAppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<PatientAppointmentDTO> appointments) {
        this.appointments = appointments;
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
                // ", appointments=" + appointments +
                //", favorites=" + favorites +
                '}';
    }
}
