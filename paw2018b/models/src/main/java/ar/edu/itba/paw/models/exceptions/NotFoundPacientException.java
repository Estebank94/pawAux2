package ar.edu.itba.paw.models.exceptions;

public class NotFoundPacientException extends Exception {
    public NotFoundPacientException() {
        super();
    }

    public NotFoundPacientException(String message) {
        super(message);
    }
}
