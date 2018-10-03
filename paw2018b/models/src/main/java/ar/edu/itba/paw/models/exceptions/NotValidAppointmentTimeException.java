package ar.edu.itba.paw.models.exceptions;

public class NotValidAppointmentTimeException extends Exception{
    public NotValidAppointmentTimeException() {
        super();
    }

    public NotValidAppointmentTimeException(String message) {
        super(message);
    }
}
