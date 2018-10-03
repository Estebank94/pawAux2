package ar.edu.itba.paw.models.exceptions;

public class RepeatedLicenceException extends Exception {
    public RepeatedLicenceException() {
        super();
    }

    public RepeatedLicenceException(String message) {
        super(message);
    }
}
