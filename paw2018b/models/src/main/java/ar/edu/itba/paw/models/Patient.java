package ar.edu.itba.paw.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Patient {

    private Integer patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Integer doctorId;
    private Set<Appointment> appointments;

    public Patient(Integer patientId, String firstName, String lastName, String phoneNumber, String email, String password) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.doctorId = null;
    }

    public Patient(Integer patientId, String firstName, String lastName, String phoneNumber, String email, String password, Integer doctorId) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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
                if (appointmentIterator.getAppointmentDay().isAfter(today)){
                    returnSet.add(appointmentIterator);
                } else if (appointmentIterator.getAppointmentDay().isEqual(today) && appointmentIterator.getAppointmentTime().isAfter(now)){
                    returnSet.add(appointmentIterator);
                }
            }
            return returnSet;

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
                    appointments.put(appoint.getAppointmentDay(),list);
                }
            }
            return appointments;


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
                appointments.put(appoint.getAppointmentDay(),list);
            }
        }
        return appointments;


    }

}
