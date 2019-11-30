package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PatientForm {

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

    @Length(min=6, max=55)
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+" )
    private String passwordConfirmation;

        // TODO
//    @Pattern(regexp = "^((\\(|)(011)(\\)|)(|\\s)(\\d{8}|\\d{4}\\s\\d{4}))$")
    @NotEmpty
    private String phoneNumber;

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

    public boolean matchingPasswords(String password, String passwordConfirmation){
        return password.equals(passwordConfirmation);
    }
}
