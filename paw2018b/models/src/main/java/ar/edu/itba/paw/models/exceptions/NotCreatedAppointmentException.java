package ar.edu.itba.paw.models.exceptions;

public class NotCreatedAppointmentException extends Exception {
    public NotCreatedAppointmentException() {
        super();
    }

    public NotCreatedAppointmentException(String message) {
        super(message);
    }
}
