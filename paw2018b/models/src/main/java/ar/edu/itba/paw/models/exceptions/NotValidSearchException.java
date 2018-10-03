package ar.edu.itba.paw.models.exceptions;

public class NotValidSearchException extends Exception {
    public NotValidSearchException() {
        super();
    }

    public NotValidSearchException(String message) {
        super(message);
    }
}
