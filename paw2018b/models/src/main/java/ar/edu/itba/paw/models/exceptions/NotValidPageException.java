package ar.edu.itba.paw.models.exceptions;

public class NotValidPageException extends Exception {
    public NotValidPageException() {
        super();
    }

    public NotValidPageException(String message) {
        super(message);
    }
}
