package ar.edu.itba.paw.models.exceptions;

public class NotValidPatientIdException extends Exception {
    public NotValidPatientIdException() {
        super();
    }

    public NotValidPatientIdException(String message) {
        super(message);
    }
}
