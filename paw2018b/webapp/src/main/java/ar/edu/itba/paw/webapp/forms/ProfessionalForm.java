package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;
import ar.edu.itba.paw.models.WorkingHours;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class ProfessionalForm {

    private String[] WORKING_HOURS = new String[]{"00:00","01:00", "02:00", "03:00", "04:00", "05:00","06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00",
            "21:00", "22:00","23:00"};


    private MultipartFile avatar;

    @Length(max=250)
    private String certificate;

    @Length(max=250)
    private String education;

    private String languages;

    @NotNull
    private List<String> insurance;
    @NotNull
    private List<String> insurancePlan;
    @NotNull
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

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
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

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public List<String> getInsurance() {
        return insurance;
    }

    public void setInsurance(List<String> insurance) {
        this.insurance = insurance;
    }

    public String[] getWORKING_HOURS() {
        return WORKING_HOURS;
    }

    public List<String> getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(List<String> insurancePlan) {
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
        if(getMonStart()!=null && getMonEnd()!=null){
            list.add(new WorkingHours(DayOfWeek.MONDAY.getValue(), getMonStart().toString(), getMonEnd().toString()));
        }
        if(getTueStart() != null && getTueEnd()!=null){
            list.add(new WorkingHours(DayOfWeek.TUESDAY.getValue(), getTueStart().toString(), getTueEnd().toString()));
        }
        if(getWedStart() != null && getWedEnd()!=null){
            list.add(new WorkingHours(DayOfWeek.WEDNESDAY.getValue(), getWedStart().toString(), getWedEnd().toString()));
        }
        if(getThuStart() != null && getThuEnd()!=null){
            list.add(new WorkingHours(DayOfWeek.THURSDAY.getValue(), getThuStart().toString(), getThuEnd().toString()));
        }
        if(getFriStart() !=null && getFriEnd()!=null){
            list.add(new WorkingHours(DayOfWeek.FRIDAY.getValue(), getFriStart().toString(), getFriEnd().toString()));
        }
        if(getSatStart() != null && getSatEnd()!=null){
            list.add(new WorkingHours(DayOfWeek.SATURDAY.getValue(), getSatStart().toString(), getSatEnd().toString()));
        }
        return list;
    }

    public Set<Specialty> getSpecialties(){
        Set<Specialty> list = new HashSet<>();
        for(String s : getSpecialty()){
            list.add(new Specialty(s));
        }
        return (list.isEmpty() ? Collections.emptySet() : list);
    }

    public List<InsurancePlan> getInsurancePlans(){
        List<InsurancePlan> list = new ArrayList<>();
        for(String s : getInsurancePlan()){
            List<String> plans = insuranceParser(s);
            for(String p : plans){
                list.add(new InsurancePlan(p));
            }
        }
        return (list.isEmpty() ? Collections.EMPTY_LIST : list);
    }

    /* Esto me deberia devolver un List de Strings de 2 */
    private List<String> insuranceParser(String insurancePlan){
        List<String> l = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        char[] c = insurancePlan.toCharArray();

        for(int i = 0; i<insurancePlan.length(); i++){
            if(c[i] == ','){
                l.add(s.toString());
                s = new StringBuilder();
            }else {
                s.append(c[i]);
            }
        }
        l.add(s.toString());
        return l;
    }
}



//    WorkingHours(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime finishTime )