package ar.edu.itba.paw.models.exceptions;

public class NotCreatedFavoriteException extends Exception {

    public NotCreatedFavoriteException() {
        super();
    }

    public NotCreatedFavoriteException(String message) {
        super(message);
    }
}
