package ar.edu.itba.paw.webapp.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PatientForm {

    @Length(min=3, max=45, message = "Cantidad de caracteres incorrecto. Su nombre debe contener entre 3 y 45 caracteres")
    @NotEmpty(message = "Este campo es obligatorio. Por favor, ingrese su nombre")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*$", message = "Su nombre no puede contener simbolos que no sean letras y espacios")
    private String firstName;

    @Length(min=2, max=45, message = "Cantidad de caracteres incorrecto. Su apellido debe contener entre 2 y 45 caracteres")
    @NotEmpty(message = "Este campo es obligatorio. Por favor, ingrese su apellido")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*$", message = "Su nombre no puede contener simbolos que no sean letras y espacios")
    private String lastName;

    @Email(message = "El email ingresado es invalido. Por favor, ingrese un email valido")
    @NotEmpty(message = "Este campo es obligatorio. Por favor, ingrese su email")
    private String email;

    @Length(min=6, max=55, message = "Cantidad de caracteres incorrecto. Su contraseña debe contener entre 6 y 55 caracteres y un número")
    @NotEmpty(message = "Este campo es obligatorio.Por favor, ingrese una contraseña")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Su contraseña debe contener al menos un número. Por favor, reingrese una contraseña valida")
    private String password;

    @Length(min=6, max=55, message = "Cantidad de caracteres incorrecto. Su contraseña debe contener entre 6 y 55 caracteres")
    @NotEmpty(message = "Este campo es obligatorio.Por favor, ingrese una contraseña")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Su contraseña debe contener al menos un número. Por favor, reingrese una contraseña valida")
    private String passwordConfirmation;


    @Pattern(regexp = "^((\\(|)(011)(\\)|)(|\\s)(\\d{8}|\\d{4}\\s\\d{4}))$", message = "Su numero de telefono es incorrecto. Recuerde que el formato es : (Codigo de Area) Numero de Telefono ")
    @NotEmpty(message = "Este campo es obligatorio. Por favor, ingrese su número de teléfono")
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
