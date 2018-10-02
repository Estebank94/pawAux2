package ar.edu.itba.paw.models.exceptions;

public class NotValidFirstNameException extends Exception {
    public NotValidFirstNameException() {
        super();
    }

    public NotValidFirstNameException(String message) {
        super(message);
    }
}
