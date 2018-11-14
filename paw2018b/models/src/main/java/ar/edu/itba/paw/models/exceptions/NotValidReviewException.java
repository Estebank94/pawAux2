package ar.edu.itba.paw.models.exceptions;

public class NotValidReviewException extends Exception {
    public NotValidReviewException() {
        super();
    }

    public NotValidReviewException(String message) {
        super(message);
    }
}
