package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.WorkingHours;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ProfessionalForm {

    private String[] WORKING_HOURS= new String[]{"06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
            "21:00", "22:00"};
    private String avatar;

    private String certificate;

    private String education;

    private Set<String> languages;
    private List<String> insurance;
    private List<Set<String>> insurancePlan;
    private Set<String> specialty;

    private LocalTime monStart;
    private LocalTime monEnd;

    private LocalTime tueStart;
    private LocalTime tueEnd;

    private LocalTime wedStart;
    private LocalTime wedEnd;

    private LocalTime thuStart;
    private LocalTime thuEnd;

    private LocalTime friStart;
    private LocalTime friEnd;

    private LocalTime satStart;
    private LocalTime satEnd;


    public void setWORKING_HOURS(String[] WORKING_HOURS) {
        this.WORKING_HOURS = WORKING_HOURS;
    }

    public LocalTime getMonStart() {
        return monStart;
    }

    public void setMonStart(LocalTime monStart) {
        this.monStart = monStart;
    }

    public LocalTime getMonEnd() {
        return monEnd;
    }

    public void setMonEnd(LocalTime monEnd) {
        this.monEnd = monEnd;
    }

    public LocalTime getTueStart() {
        return tueStart;
    }

    public void setTueStart(LocalTime tueStart) {
        this.tueStart = tueStart;
    }

    public LocalTime getTueEnd() {
        return tueEnd;
    }

    public void setTueEnd(LocalTime tueEnd) {
        this.tueEnd = tueEnd;
    }

    public LocalTime getWedStart() {
        return wedStart;
    }

    public void setWedStart(LocalTime wedStart) {
        this.wedStart = wedStart;
    }

    public LocalTime getWedEnd() {
        return wedEnd;
    }

    public void setWedEnd(LocalTime wedEnd) {
        this.wedEnd = wedEnd;
    }

    public LocalTime getThuStart() {
        return thuStart;
    }

    public void setThuStart(LocalTime thuStart) {
        this.thuStart = thuStart;
    }

    public LocalTime getThuEnd() {
        return thuEnd;
    }

    public void setThuEnd(LocalTime thuEnd) {
        this.thuEnd = thuEnd;
    }

    public LocalTime getFriStart() {
        return friStart;
    }

    public void setFriStart(LocalTime friStart) {
        this.friStart = friStart;
    }

    public LocalTime getFriEnd() {
        return friEnd;
    }

    public void setFriEnd(LocalTime friEnd) {
        this.friEnd = friEnd;
    }

    public LocalTime getSatStart() {
        return satStart;
    }

    public void setSatStart(LocalTime satStart) {
        this.satStart = satStart;
    }

    public LocalTime getSatEnd() {
        return satEnd;
    }

    public void setSatEnd(LocalTime satEnd) {
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

    public List<WorkingHours> workingHoursList() {

        List<WorkingHours> list = new ArrayList<>();
        list.add(new WorkingHours(DayOfWeek.MONDAY, getMonStart(), getMonEnd()));
        list.add(new WorkingHours(DayOfWeek.TUESDAY, getTueStart(), getTueEnd()));
        list.add(new WorkingHours(DayOfWeek.WEDNESDAY, getWedStart(), getWedEnd()));
        list.add(new WorkingHours(DayOfWeek.THURSDAY, getThuStart(), getThuEnd()));
        list.add(new WorkingHours(DayOfWeek.FRIDAY, getFriStart(), getFriEnd()));

        return list;
    }
}


//    WorkingHours(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime finishTime )