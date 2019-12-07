package ar.edu.itba.paw.models.exceptions;

public class NotFoundAppointmentException extends Exception  {
    public NotFoundAppointmentException() {
        super();
    }

    public NotFoundAppointmentException(String message) {
        super(message);
    }
}
