package ar.edu.itba.paw.models.exceptions;

public class NotValidIDException extends Exception {
    public NotValidIDException() {
        super();
    }

    public NotValidIDException(String message) {
        super(message);
    }
}
