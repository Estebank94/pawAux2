package ar.edu.itba.paw.models;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name="patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="patient_id_seq")
    @SequenceGenerator(sequenceName = "patient_id_seq", name = "patient_id_seq", allocationSize = 1)
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    @OneToMany(mappedBy = "patient")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Appointment> appointments;

    @OneToOne
    @JoinColumn(name = "doctorid")
    private Doctor doctor;

    @OneToMany(mappedBy="patient", cascade = {CascadeType.PERSIST})
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Favorite> favorites;


    public Patient( String firstName, String lastName, String phoneNumber, String email, String password) {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public Patient(Integer id, String firstName, String lastName, String phoneNumber, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public Patient(Integer id, String firstName, String lastName, String phoneNumber, String email, String password, Integer doctorId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    @Autowired
    public Patient(){
    }

    public Integer getPatientId() {
        return id;
    }

    public void setPatientId(Integer patientId) {
        this.id = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Set<Appointment> getFutureAppointments(){
        Set<Appointment> returnSet = new HashSet<>();
        Set<Appointment> appointments = getAppointments();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        for (Appointment appointmentIterator: appointments){
            if (LocalDate.parse(appointmentIterator.getAppointmentDay()).isAfter(today) && !appointmentIterator.getAppointmentCancelled()){
                returnSet.add(appointmentIterator);
            } else if (LocalDate.parse(appointmentIterator.getAppointmentDay()).isEqual(today) && LocalTime.parse(appointmentIterator.getAppointmentTime()).isAfter(now)
                    && !appointmentIterator.getAppointmentCancelled()){
                returnSet.add(appointmentIterator);
            }
        }
        return returnSet;
    }

    public Map<LocalDate, List<Appointment>> appointmentsMap (){

        Map<LocalDate, List<Appointment>> appointments = new HashMap<>();
        Set<Appointment> all = getFutureAppointments();

        for(Appointment appoint : all){
            if(appointments.containsKey(LocalDate.parse(appoint.getAppointmentDay()))){
                appointments.get(LocalDate.parse(appoint.getAppointmentDay())).add(appoint);
            }else{
                List<Appointment> list = new ArrayList<>();
                list.add(appoint);
                appointments.put(LocalDate.parse(appoint.getAppointmentDay()),list);
            }
        }
        return appointments;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @NotNull
    private LocalDateTime getAppointmentDateTime (Appointment ap){
        LocalDate appointementDay = LocalDate.parse(ap.getAppointmentDay());
        return appointementDay.atTime(LocalTime.parse(ap.getAppointmentTime()));
    }

    private void addAppointmentInOrderToList(List<Appointment> list, Appointment appointment, LocalDateTime appointmentDateTime){
        if (list.isEmpty()){
            list.add(appointment);
            return;
        }
        int index;
        for (index = 0 ; index < list.size() ; index++ ){
            if (appointmentDateTime.isAfter(getAppointmentDateTime(list.get(index)))){
                list.add(index, appointment);
                return;
            }
        }
        list.add(list.size() - 1, appointment );
        return;
    }

    public List<Appointment> getHistoricalAppointments(){
        LocalDateTime currentAppointmentTime;

        Set<Appointment> appointments = getAppointments();
        List<Appointment> retList = new LinkedList<>();

        LocalDateTime now = LocalDateTime.now();

        for (Appointment appointmentIterator: appointments){
            if (!appointmentIterator.getAppointmentCancelled()) {
                currentAppointmentTime = getAppointmentDateTime(appointmentIterator);
                if (currentAppointmentTime.isBefore(now)) {
                    addAppointmentInOrderToList(retList, appointmentIterator, currentAppointmentTime);
                }
            }

        }
        return retList;
    }

    public Boolean isFavorite(Doctor doctor){
        for(Favorite favorite : favorites){
            if(favorite.getDoctor().equals(doctor)){
                if (favorite.getFavoriteCancelled()){
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Doctor> getFavoriteDoctors(){
        List<Favorite> fav = getFavorites();
        List<Doctor> ans = new ArrayList<>();
        for (Favorite favoriteIterator : fav){
            if (!favoriteIterator.getFavoriteCancelled()){
                ans.add(favoriteIterator.getDoctor());
            }
        }
        return ans;
    }
}
