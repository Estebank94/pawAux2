package ar.edu.itba.paw.models.exceptions;

public class NotCreatePatientException extends Exception {
    public NotCreatePatientException() {
        super();
    }

    public NotCreatePatientException(String message) {
        super(message);
    }
}
