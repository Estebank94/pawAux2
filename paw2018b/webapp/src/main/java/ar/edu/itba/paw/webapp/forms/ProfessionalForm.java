package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

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
    private Set<String> insurance;
    private List<String> insurancePlan;
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

    public Set<String> getInsurance() {
        return insurance;
    }

    public void setInsurance(Set<String> insurance) {
        this.insurance = insurance;
    }

    public List<String>  getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(List<String>  insurancePlan) {
        this.insurancePlan = insurancePlan;
    }

}

