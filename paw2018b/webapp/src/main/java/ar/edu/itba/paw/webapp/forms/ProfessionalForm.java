package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProfessionalForm {


    private String avatar;
    @NotEmpty
    private String description;
    @NotEmpty
    private String education;

    private Set<String> languages;
    private List<String> insurance;
    private List<List<String>> insurancePlan;
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

    public List<List<String>>  getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(List<List<String>>  insurancePlan) {
        this.insurancePlan = insurancePlan;
    }

    public Map<String, List<String>> createMap(List<String> insurance, List<List<String>> insurancePlan) {

        Map<String, List<String>> map = new HashMap<>();

        for(int i = 0; i<insurance.size(); i++){
            map.put(insurance.get(i), insurancePlan.get(i));
        }

        return map;
    }
}

