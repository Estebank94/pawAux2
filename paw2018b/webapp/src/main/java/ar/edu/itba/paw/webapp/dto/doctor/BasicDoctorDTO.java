package ar.edu.itba.paw.webapp.dto.doctor;

import ar.edu.itba.paw.models.Doctor;

public class BasicDoctorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String sex;
    private String address;
    private String phoneNumber;

    public BasicDoctorDTO() {
    }

    public BasicDoctorDTO (Doctor doctor){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.address = doctor.getAddress();
        this.sex = doctor.getSex();
        this.phoneNumber = doctor.getPhoneNumber();
        this.lastName = doctor.getLastName();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
