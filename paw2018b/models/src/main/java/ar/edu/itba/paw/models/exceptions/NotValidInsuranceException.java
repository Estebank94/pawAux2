package ar.edu.itba.paw.models.exceptions;

public class NotValidInsuranceException extends Exception {
    public NotValidInsuranceException() {
        super();
    }

    public NotValidInsuranceException(String message) {
        super(message);
    }
}
