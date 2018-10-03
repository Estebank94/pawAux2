package ar.edu.itba.paw.models.exceptions;

public class NotValidAddressException extends Exception {
    public NotValidAddressException() {
        super();
    }

    public NotValidAddressException(String message) {
        super(message);
    }
}
