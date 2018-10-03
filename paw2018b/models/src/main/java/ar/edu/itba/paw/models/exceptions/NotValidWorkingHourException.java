package ar.edu.itba.paw.models.exceptions;

public class NotValidWorkingHourException extends Exception {
    public NotValidWorkingHourException() {
        super();
    }

    public NotValidWorkingHourException(String message) {
        super(message);
    }
}
