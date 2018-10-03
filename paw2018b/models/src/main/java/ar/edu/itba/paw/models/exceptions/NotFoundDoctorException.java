package ar.edu.itba.paw.models.exceptions;

public class NotFoundDoctorException extends Exception {
    public NotFoundDoctorException() {
        super();
    }

    public NotFoundDoctorException(String message) {
        super(message);
    }
}
