package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Patient;

public class BasicPatientDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public BasicPatientDTO() {
    }

    public BasicPatientDTO(Patient patient){
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.phoneNumber = patient.getPhoneNumber();
        this.email = patient.getEmail();
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
}
