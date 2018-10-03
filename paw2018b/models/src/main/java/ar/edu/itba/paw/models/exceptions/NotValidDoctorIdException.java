package ar.edu.itba.paw.models.exceptions;

public class NotValidDoctorIdException extends Exception {
    public NotValidDoctorIdException() {
        super();
    }

    public NotValidDoctorIdException(String message) {
        super(message);
    }
}
