package ar.edu.itba.paw.models.exceptions;

public class NotValidPasswordException extends Exception {
    public NotValidPasswordException() {
        super();
    }

    public NotValidPasswordException(String message) {
        super(message);
    }
}