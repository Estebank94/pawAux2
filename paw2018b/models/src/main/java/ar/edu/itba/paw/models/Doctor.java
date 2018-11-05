package ar.edu.itba.paw.models;

import ar.edu.itba.paw.App;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by estebankramer on 31/08/2018.
 */

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName;
    String lastName;
    String sex;
    String address;
    String avatar;
    @ManyToMany(cascade = {CascadeType.ALL},
                fetch = FetchType.EAGER
    )
    @JoinTable(
            name="doctorSpecialty",
            joinColumns = {@JoinColumn(name = "doctorid", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name = "specialtyid", referencedColumnName="id")}
    )
    Set<Specialty> specialties;

    @ManyToMany(cascade = {CascadeType.PERSIST},
                fetch = FetchType.EAGER
    )
    @JoinTable(
            name="medicalCare",
            joinColumns = {@JoinColumn(name="doctorid", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="insuranceplanid", referencedColumnName="id")}
    )
    List<InsurancePlan> insurancePlans;

    String phoneNumber;

    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.ALL})
    List<WorkingHours> workingHours;


    @OneToMany(mappedBy = "doctor")
    Set<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    List<Review> reviews;

    @OneToOne(mappedBy="doctor")
    Patient patient;

    @OneToOne(mappedBy="doctor")
    Description description;

    Integer licence;
    String district;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public void addWorkingHours(Collection<WorkingHours> workingHours) {
        this.workingHours.addAll(workingHours);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getLicence() {
        return licence;
    }

    public void setLicence(Integer licence) {
        this.licence = licence;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    @Autowired
    public Doctor(){

    }

    public Doctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence, String avatar, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.address = address;
        this.avatar = avatar;
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

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
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

    public List<InsurancePlan> getInsurancePlans() {
        return insurancePlans;
    }

    public void setInsurancePlans(List<InsurancePlan> insurancePlans) {
        this.insurancePlans = insurancePlans;
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


    public Map<LocalDate, List<Appointment>>getAvailableAppointments(){
        Map<LocalDate, List<Appointment>> map = new HashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i<15; i++){
            List<Appointment> aux = generateAppointments(today.plusDays(i));
            if(aux != null){
                map.put(today.plusDays(i),aux);
            }
        }
        return map;
    }

    private List<WorkingHours> getWorkingHoursByDay(DayOfWeek dow){
        List<WorkingHours> ans = new ArrayList<>();
        for (WorkingHours wh: workingHours){
            DayOfWeek day = DayOfWeek.of(wh.getDayOfWeek());
            if (day.equals(dow)){
                ans.add(wh);
            }
        }
        return ans;
    }

    private List<Appointment> generateAppointments(LocalDate date) {
        List<WorkingHours> workingHours = getWorkingHoursByDay(date.getDayOfWeek());

        List<Appointment> list = new ArrayList<>();
        Set<Appointment> futureAppointments = getFutureAppointments();
        boolean flag;
        int i;
        if(workingHours != null){
            for (WorkingHours workingHoursIterator: workingHours){
                flag = true;
                for (i = 0; flag; i++){
                    if (LocalTime.parse(workingHoursIterator.getStartTime()).plusMinutes(WorkingHours.APPOINTMENTTIME_TIME * i).isAfter(LocalTime.parse(workingHoursIterator.getFinishTime())) || (LocalTime.parse(workingHoursIterator.getStartTime()).plusMinutes(WorkingHours.APPOINTMENTTIME_TIME * i).compareTo(LocalTime.parse(workingHoursIterator.getFinishTime())) == 0)){
                        flag = false;
                    } else{

                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String formattedDate = date.format(dateFormatter);

                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                        String formattedTime = LocalTime.parse(workingHoursIterator.getStartTime()).plusMinutes(WorkingHours.APPOINTMENTTIME_TIME * i).format(timeFormatter);

                        Appointment dateAppointment = new Appointment(formattedDate,formattedTime, patient);
                        if (!futureAppointments.contains(dateAppointment)){
                            list.add(dateAppointment);
                        }
                    }
                }
            }
            Collections.sort(list);
            return list;
        }
        return null;
    }


    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHoursMap(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Appointment> getFutureAppointments(){
        Set<Appointment> returnSet = new HashSet<>();
        Set<Appointment> appointments = getAppointments();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        for (Appointment appointmentIterator: appointments){
            if (LocalDate.parse(appointmentIterator.getAppointmentDay()).isAfter(today)){
                returnSet.add(appointmentIterator);
            } else if (LocalDate.parse(appointmentIterator.getAppointmentDay()).isEqual(today) && LocalTime.parse(appointmentIterator.getAppointmentTime()).isAfter(now)){
                returnSet.add(appointmentIterator);
            }
        }
        return returnSet;
    }
//
//    public List<Review> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(List<Review> reviews) {
//        this.reviews = reviews;
//    }

    public Map<LocalDate, List<LocalTime>> appointmentsToMap (){

        Map<LocalDate, List<LocalTime>> appointments = new HashMap<>();
        Set<Appointment> all = getFutureAppointments();
        for(Appointment appoint : all){
            if(appointments.containsKey(appoint.getAppointmentDay())){
                appointments.get(appoint.getAppointmentDay()).add(LocalTime.parse(appoint.getAppointmentTime()));
            }else{
                List<LocalTime> list = new ArrayList<>();
                list.add(LocalTime.parse(appoint.getAppointmentTime()));
                appointments.put(LocalDate.parse(appoint.getAppointmentDay()),list);
            }
        }
        return  appointments;
    }

    public Map<LocalDate, List<Appointment>> appointmentsMap (){

        Map<LocalDate, List<Appointment>> appointments = new HashMap<>();
        Set<Appointment> all = getFutureAppointments();
        for(Appointment appoint : all){
            if(appointments.containsKey(appoint.getAppointmentDay())){
                appointments.get(appoint.getAppointmentDay()).add(appoint);
            }else{
                List<Appointment> list = new ArrayList<>();
                list.add(appoint);
                appointments.put(LocalDate.parse(appoint.getAppointmentDay()),list);
            }
        }
        return  appointments;
    }

    public String getMonth(Integer monthVal){
        String month = "";

        switch (monthVal){
            case 1: month = "Enero";
                break;
            case 2: month = "Febrero";
                break;
            case 3: month = "Marzo";
                break;
            case 4: month = "Abril";
                break;
            case 5: month = "Mayo";
                break;
            case 6: month = "Junio";
                break;
            case 7: month = "Julio";
                break;
            case 8: month = "Agosto";
                break;
            case 9: month = "Septiembre";
                break;
            case 10: month = "Octubre";
                break;
            case 11: month = "Noviembre";
                break;
            case 12: month = "Diciembre";
                break;
        }
        return month;
    }

    public Set<DayOfWeek> emptyWorkingHours(){
        Set<DayOfWeek> days = new HashSet<>();
        for(DayOfWeek day : DayOfWeek.values()){
            days.add(day);
        }
        for (WorkingHours wh: workingHours){
            if (days.contains(wh.getDayOfWeek())){
                days.remove(wh.getDayOfWeek());
            }
            if (days.isEmpty()){
                return days;
            }
        }
        return days;
    }

    public boolean containsSpecialty(Set<String> specialtySet){


        for(String specia : specialtySet){
            if(getSpecialties().contains(specia)){
                return true;
            }
        }
        return false;
    }

    public boolean containsPlan(List<String> plans){

        for(String plan : plans){
            if(getInsurancePlans().contains(plan)){
                return true;
            }
        }
        return false;

//        for(InsurancePlan wantedPlan : plans){
//            for(InsurancePlan insurancePlan : insurancePlans){
//                if(wantedPlan.equals(insurancePlan)){
//                    return true;
//                }
//            }
//        }
//        return false;
    }

    public void addSpecialties(Set<Specialty> specialties){
        getSpecialties().addAll(specialties);
    }

    public void addInsurancePlans(List<InsurancePlan> plans){
        getInsurancePlans().addAll(plans);
    }

    public List<Insurance> getInsuranceListFromInsurancePlans(){
        List<Insurance> list = new ArrayList<>();

        for(InsurancePlan plan : getInsurancePlans()){
            if(!list.contains(plan.getInsurance())){
                list.add(plan.getInsurance());
            }
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    public List<InsurancePlan> getInsurancePlansFromInsurance(Insurance insurance){
        List<InsurancePlan> list = new ArrayList<>();

        for(InsurancePlan plan : getInsurancePlans()){
            if(plan.getInsurance().equals(insurance) && !list.contains(plan)){
                list.add(plan);
            }
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

}


