package ar.edu.itba.paw.models.exceptions;

public class NotValidCancelAppointment extends Exception {

    public NotValidCancelAppointment() {
        super();
    }

    public NotValidCancelAppointment(String message) {
        super(message);
    }
}
