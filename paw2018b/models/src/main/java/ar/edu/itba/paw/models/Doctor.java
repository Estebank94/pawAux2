package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by estebankramer on 31/08/2018.
 */

public class Doctor {
    String firstName;
    String lastName;
    String sex;
    String address;
    String avatar;
    Set<String> specialty;
    Map<String, Set<String>> insurance;
    String workingHours;
    Integer id;
    Description description;
    String phoneNumber;

    @Autowired
    public Doctor(String firstName, String lastName, String sex, String address, String avatar, Set<String> specialty,Map<String, Set<String>> insurance, String workingHours, Integer id, Description description, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.address = address;
        this.avatar = avatar;
        this.specialty = specialty;
        this.insurance = insurance;
        this.workingHours = workingHours;
        this.id = id;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public Doctor(String firstName, String lastName, String sex, String avatar, String address, Integer id, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.address = address;
        this.avatar = avatar;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Set<String> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Set<String> specialty) {
        this.specialty = specialty;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatar() {
        return avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Set<String>> getInsurance() {
        return insurance;
    }

    public void setInsurance(Map<String, Set<String>> insurance) {
        this.insurance = insurance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(getId(), doctor.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}


