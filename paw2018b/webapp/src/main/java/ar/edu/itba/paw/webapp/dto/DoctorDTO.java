package ar.edu.itba.paw.webapp.dto;
import ar.edu.itba.paw.models.Doctor;

public class DoctorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String sex;
    private String address;
    private String phoneNumber;
    private byte[] profilePicture;
    private SpecialtyListDTO specialties;
    private InsurancePlanListDTO insurancesPlans;
    private WorkingHoursListDTO workingHours;
    private ReviewListDTO reviews;
    private DescriptionDTO description;
    private Integer averageRating;
    private Integer licence;
    private String district;


    // private URI uri;
    /*
    public DoctorDTO(Doctor doctor, URI baseURI){
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.sex = doctor.getSex();
        this.address = doctor.getAddress();
        this.phoneNumber = doctor.getPhoneNumber();
       // this.uri = baseURI.resolve(String.valueOf(this.id));
    }
    */
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
        this.specialties = new SpecialtyListDTO(doctor.getSpecialties());
        this.insurancesPlans = new InsurancePlanListDTO(doctor.getInsurancePlans());
        this.workingHours = new WorkingHoursListDTO(doctor.getWorkingHours());
        this.reviews = new ReviewListDTO(doctor.getReviews());
        this.averageRating = doctor.calculateAverageRating();
        this.licence = doctor.getLicence();
        this.district = doctor.getDistrict();
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

    public SpecialtyListDTO getSpecialties() {
        return specialties;
    }

    public void setSpecialties(SpecialtyListDTO specialties) {
        this.specialties = specialties;
    }

    public InsurancePlanListDTO getInsurancesPlans() {
        return insurancesPlans;
    }

    public void setInsurancesPlans(InsurancePlanListDTO insurancesPlans) {
        this.insurancesPlans = insurancesPlans;
    }

    public WorkingHoursListDTO getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(WorkingHoursListDTO workingHours) {
        this.workingHours = workingHours;
    }

    public ReviewListDTO getReviews() {
        return reviews;
    }

    public void setReviews(ReviewListDTO reviews) {
        this.reviews = reviews;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
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
