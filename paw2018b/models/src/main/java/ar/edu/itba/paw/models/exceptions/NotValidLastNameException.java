package ar.edu.itba.paw.models.exceptions;

public class NotValidLastNameException extends Exception {
    public NotValidLastNameException() {
        super();
    }

    public NotValidLastNameException(String message) {
        super(message);
    }
}
