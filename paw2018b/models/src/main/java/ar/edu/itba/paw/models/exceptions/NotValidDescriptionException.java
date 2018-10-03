package ar.edu.itba.paw.models.exceptions;

public class NotValidDescriptionException extends Exception{
    public NotValidDescriptionException() {
        super();
    }

    public NotValidDescriptionException(String message) {
        super(message);
    }
}
