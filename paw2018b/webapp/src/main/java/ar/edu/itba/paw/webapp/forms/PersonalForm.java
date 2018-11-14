package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PersonalForm {

    @Length(min=3, max=45)
    @NotEmpty
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*$")
    private String firstName;

    @Length(min=2, max=45)
    @NotEmpty
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*$")
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    @Length(min=6, max=55)
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String password;

    @Length(min=6, max=10)
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String passwordConfirmation;

    @Pattern(regexp = "^((\\(|)(011)(\\)|)(|\\s)(\\d{8}|\\d{4}\\s\\d{4}))$")
    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String address;

    @NotEmpty
    private String sex;

    @Length(max=10)
    @Pattern(regexp = "[0-9]{1,10}")
    private String licence;

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean matchingPasswords(String password, String passwordConfirmation){
        return password.equals(passwordConfirmation);
    }
}

