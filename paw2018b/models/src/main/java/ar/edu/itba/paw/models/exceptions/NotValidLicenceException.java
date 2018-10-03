package ar.edu.itba.paw.models.exceptions;

public class NotValidLicenceException extends Exception {
    public NotValidLicenceException() {
        super();
    }

    public NotValidLicenceException(String message) {
        super(message);
    }
}
