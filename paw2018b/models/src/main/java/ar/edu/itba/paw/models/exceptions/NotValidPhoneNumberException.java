package ar.edu.itba.paw.models.exceptions;

public class NotValidPhoneNumberException extends Exception {
    public NotValidPhoneNumberException() {
        super();
    }

    public NotValidPhoneNumberException(String message) {
        super(message);
    }
}
