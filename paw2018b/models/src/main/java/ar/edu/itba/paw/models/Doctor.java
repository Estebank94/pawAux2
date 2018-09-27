package ar.edu.itba.paw.models;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    //String workingHours;
    Integer id;
    Description description;
    String phoneNumber;
    Map< DayOfWeek, List<WorkingHours>> workingHours;
    Set<Appointment> appointments;
    List<Review> reviews;



    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Autowired
    public Doctor(String firstName, String lastName, String sex, String address, String avatar, Set<String> specialty,Map<String, Set<String>> insurance, Integer id, Description description, String phoneNumber, Map<DayOfWeek,List<WorkingHours>> workingHours) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.address = address;
        this.avatar = avatar;
        this.specialty = specialty;
        this.insurance = insurance;
        this.id = id;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.workingHours = workingHours;
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

 //   public String getWorkingHours() {
  //      return workingHours;
   // }

   // public void setWorkingHours(String workingHours) {
   //    this.workingHours = workingHours;
   // }

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


    public Map<LocalDate, List<Appointment>>getAvailableAppointments(){
        Map<LocalDate, List<Appointment>> map =new HashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i<15; i++){
            map.put(today.plusDays(i),generateAppointments(today.plusDays(i)));
        }
        return map;
    }

    private List<Appointment> generateAppointments(LocalDate date) {
        List<WorkingHours> workingHours = getWorkingHours().get(date.getDayOfWeek());
        List<Appointment> list = new ArrayList<>();
        boolean flag;
        int i;
        for (WorkingHours workingHoursIterator: workingHours){
            flag = true;
            for (i = 0; flag; i++){
                if (workingHoursIterator.getStartTime().plusMinutes(WorkingHours.APPOINTMENTTIME_TIME*i).isAfter(workingHoursIterator.getFinishTime())){
                    flag = false;
                } else{
                    Appointment dateAppointment = new Appointment(date,workingHoursIterator.getStartTime().plusMinutes(WorkingHours.APPOINTMENTTIME_TIME * i));
                    if (!appointments.contains(dateAppointment)){
                        list.add(dateAppointment);
                    }
                }
            }
        }
        Collections.sort(list);
        return list;
    }


    public Map<DayOfWeek, List<WorkingHours>> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHoursMap(Map<DayOfWeek, List<WorkingHours>> workingHoursMap) {
        this.workingHours = workingHoursMap;
    }

//    public Set<Appointment> getAppointments() {
//        return appointments;
//    }
//
//    public void setAppointments(Set<Appointment> appointments) {
//        this.appointments = appointments;
//    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}


