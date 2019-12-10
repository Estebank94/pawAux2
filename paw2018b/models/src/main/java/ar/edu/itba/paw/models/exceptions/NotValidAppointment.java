package ar.edu.itba.paw.models.exceptions;

public class NotValidAppointment extends Exception {
    public NotValidAppointment() {
        super();
    }

    public NotValidAppointment(String message) {
        super(message);
    }
}
