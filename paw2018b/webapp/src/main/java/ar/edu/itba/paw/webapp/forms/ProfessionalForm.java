package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.*;

public class ProfessionalForm {

    private String[] WORKING_HOURS= new String[]{"6:00", "7:00", "8:00", "9:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
            "21:00", "22:00"};
    private String avatar;
    @NotEmpty
    private String certificate;
    @NotEmpty
    private String education;

    private Set<String> languages;
    private List<String> insurance;
    private List<Set<String>> insurancePlan;
    private Set<String> specialty;

    private String monStart;
    private String monEnd;

    private String tueStart;
    private String tueEnd;

    private String wedStart;
    private String wedEnd;

    private String thuStart;
    private String thuEnd;

    private String friStart;
    private String friEnd;

    private String satStart;
    private String satEnd;


    public void setWORKING_HOURS(String[] WORKING_HOURS) {
        this.WORKING_HOURS = WORKING_HOURS;
    }

    public String getMonStart() {
        return monStart;
    }

    public void setMonStart(String monStart) {
        this.monStart = monStart;
    }

    public String getMonEnd() {
        return monEnd;
    }

    public void setMonEnd(String monEnd) {
        this.monEnd = monEnd;
    }

    public String getTueStart() {
        return tueStart;
    }

    public void setTueStart(String tueStart) {
        this.tueStart = tueStart;
    }

    public String getTueEnd() {
        return tueEnd;
    }

    public void setTueEnd(String tueEnd) {
        this.tueEnd = tueEnd;
    }

    public String getWedStart() {
        return wedStart;
    }

    public void setWedStart(String wedStart) {
        this.wedStart = wedStart;
    }

    public String getWedEnd() {
        return wedEnd;
    }

    public void setWedEnd(String wedEnd) {
        this.wedEnd = wedEnd;
    }

    public String getThuStart() {
        return thuStart;
    }

    public void setThuStart(String thuStart) {
        this.thuStart = thuStart;
    }

    public String getThuEnd() {
        return thuEnd;
    }

    public void setThuEnd(String thuEnd) {
        this.thuEnd = thuEnd;
    }

    public String getFriStart() {
        return friStart;
    }

    public void setFriStart(String friStart) {
        this.friStart = friStart;
    }

    public String getFriEnd() {
        return friEnd;
    }

    public void setFriEnd(String friEnd) {
        this.friEnd = friEnd;
    }

    public String getSatStart() {
        return satStart;
    }

    public void setSatStart(String satStart) {
        this.satStart = satStart;
    }

    public String getSatEnd() {
        return satEnd;
    }

    public void setSatEnd(String satEnd) {
        this.satEnd = satEnd;
    }

    public String[] getWorkingHours() {
        return WORKING_HOURS;
    }

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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
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

