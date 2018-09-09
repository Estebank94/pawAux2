package ar.edu.itba.paw.models;

import java.util.List;

public class Search {

    private String name;
    private String specialty;
    private String location;
    private String insurance;
    private String sex = "ALL";
    private List<String> insurancePlan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) { this.insurance = insurance; }

    public String[] splitName(String name){

//        https://stackoverflow.com/questions/7899525/how-to-split-a-string-by-space/7899558

        String[] answer = name.split("\\s+");
        return answer;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(List<String> insurancePlan) {
        this.insurancePlan = insurancePlan;
    }
}
