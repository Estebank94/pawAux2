package ar.edu.itba.paw.webapp.dto.doctor;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Specialty;
import ar.edu.itba.paw.models.WorkingHours;
import ar.edu.itba.paw.webapp.dto.insurance.InsuranceDTO;
import ar.edu.itba.paw.webapp.dto.workingHours.WorkingHoursDTO;
import org.hibernate.Hibernate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DoctorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String sex;
    private String address;
    private String phoneNumber;
    private byte[] profilePicture;
    private DescriptionDTO description;
    private Integer averageRating;
    private Integer licence;
    private List<String> specialties;
    // private List<InsurancePlanDTO> inssurancePlans;
    private List<InsuranceDTO> insurances;
    private List<WorkingHoursDTO> workingHours;
    private URI uri;

    public DoctorDTO(Doctor doctor, URI baseURI){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.sex = doctor.getSex();
        this.address = doctor.getAddress();
        this.phoneNumber = doctor.getPhoneNumber();
        this.uri = baseURI.resolve(String.valueOf(this.id));

    }

    public DoctorDTO(){
    }

    public DoctorDTO(Doctor doctor){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.sex = doctor.getSex();
        this.address = doctor.getAddress();
        this.phoneNumber = doctor.getPhoneNumber();
        this.profilePicture = doctor.getProfilePicture();
        this.averageRating = doctor.calculateAverageRating();
        this.licence = doctor.getLicence();
        this.insurances = InsuranceDTO.insurancePlanMapping(doctor.getInsurancePlans());

        this.specialties = new ArrayList<>();
        for (Specialty sp: doctor.getSpecialties()){
            this.specialties.add(sp.getSpeciality());
        }
        this.description = null;
        if (doctor.getDescription() != null){
            this.description = new DescriptionDTO(doctor.getDescription());
        }

        this.workingHours = new ArrayList<>();
        if(doctor.getWorkingHours()!=null){
            for(WorkingHours wh : doctor.getWorkingHours()){
                this.workingHours.add(new WorkingHoursDTO(wh));
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }


    public DescriptionDTO getDescription() {
        return description;
    }

    public void setDescription(DescriptionDTO description) {
        this.description = description;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getLicence() {
        return licence;
    }

    public void setLicence(Integer licence) {
        this.licence = licence;
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public List<InsuranceDTO> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<InsuranceDTO> insurances) {
        this.insurances = insurances;
    }

    public List<WorkingHoursDTO> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHoursDTO> workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public String toString () {
        return "DoctorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
