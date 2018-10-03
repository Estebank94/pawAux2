package ar.edu.itba.paw.models.exceptions;

public class NotValidEmailException extends Exception {
    public NotValidEmailException() {
        super();
    }

    public NotValidEmailException(String message) {
        super(message);
    }
}
