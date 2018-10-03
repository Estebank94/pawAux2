package ar.edu.itba.paw.models.exceptions;

public class NotValidAppointmentDayException extends Exception {
    public NotValidAppointmentDayException() {
        super();
    }

    public NotValidAppointmentDayException(String message) {
        super(message);
    }
}
