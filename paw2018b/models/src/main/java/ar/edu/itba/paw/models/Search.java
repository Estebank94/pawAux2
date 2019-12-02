package ar.edu.itba.paw.models;

import java.util.List;

public class Search {

    private String name = "";
    private String specialty = "noSpecialty";
    private String location = "";
    private String insurance = "no";
    private String sex = "ALL";
    private List<String> insurancePlan = null;
    private List<String> days = null;

    public Search () {
    }

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

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Search{" +
                "name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", location='" + location + '\'' +
                ", insurance='" + insurance + '\'' +
                ", sex='" + sex + '\'' +
                ", insurancePlan=" + insurancePlan +
                ", days='" + days + '\'' +
                '}';
    }
}
