package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Doctor;

import java.net.URI;

public class DoctorDTO {
    Integer id;
    String firstName;
    String lastName;
    String sex;
    String address;
    String phoneNumber;
    private URI uri;

    public DoctorDTO(Doctor doctor, URI baseURI){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.sex = doctor.getSex();
        this.address = doctor.getAddress();
        this.phoneNumber = doctor.getPhoneNumber();
        this.uri = baseURI.resolve(String.valueOf(this.id));
    }

    public DoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.sex = doctor.getSex();
        this.address = doctor.getAddress();
        this.phoneNumber = doctor.getPhoneNumber();
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
