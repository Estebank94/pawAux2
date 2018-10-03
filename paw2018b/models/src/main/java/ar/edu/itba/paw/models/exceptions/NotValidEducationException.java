package ar.edu.itba.paw.models.exceptions;

public class NotValidEducationException extends Exception {
    public NotValidEducationException() {
        super();
    }

    public NotValidEducationException(String message) {
        super(message);
    }
}
