package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.*;

public class ProfessionalForm {


    private String avatar;
    @NotEmpty
    private String description;
    @NotEmpty
    private String education;

    private Set<String> languages;
    private List<String> insurance;
    private List<Set<String>> insurancePlan;
    private Set<String> specialty;

    public Set<String> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Set<String> specialty) {
        this.specialty = specialty;
    }

    @NotEmpty
    private String workingHoursStart;
    @NotEmpty
    private String workingHoursEnd;

    public String getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(String workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public String getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(String workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public List<String> getInsurance() {
        return insurance;
    }

    public void setInsurance(List<String> insurance) {
        this.insurance = insurance;
    }

    public List<Set<String>>  getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(List<Set<String>>  insurancePlan) {
        this.insurancePlan = insurancePlan;
    }

    public Map<String, Set<String>> createMap(List<String> insurance, List<Set<String>> insurancePlan) {

        Map<String, Set<String>> map = new HashMap<>();
        for (int i = 0; i< insurance.size(); i++){
            map.put(insurance.get(i),insurancePlan.get(i));
        }

        return map;
    }
}

