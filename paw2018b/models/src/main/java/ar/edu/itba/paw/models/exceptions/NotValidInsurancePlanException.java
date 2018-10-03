package ar.edu.itba.paw.models.exceptions;

public class NotValidInsurancePlanException extends Exception {
    public NotValidInsurancePlanException() {
        super();
    }

    public NotValidInsurancePlanException(String message) {
        super(message);
    }
}
