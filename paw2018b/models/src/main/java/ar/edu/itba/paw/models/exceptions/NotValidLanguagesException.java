package ar.edu.itba.paw.models.exceptions;

public class NotValidLanguagesException extends Exception {
    public NotValidLanguagesException() {
        super();
    }

    public NotValidLanguagesException(String message) {
        super(message);
    }
}
