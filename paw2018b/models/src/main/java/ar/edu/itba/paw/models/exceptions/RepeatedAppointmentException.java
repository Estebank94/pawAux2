package ar.edu.itba.paw.models.exceptions;

public class RepeatedAppointmentException extends Exception {
    public RepeatedAppointmentException() {
        super();
    }

    public RepeatedAppointmentException(String message) {
        super(message);
    }
}
