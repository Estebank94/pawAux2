package ar.edu.itba.paw.models.exceptions;

public class NotRemoveFavoriteException extends Exception{
    public NotRemoveFavoriteException() {
        super();
    }

    public NotRemoveFavoriteException(String message) {
        super(message);
    }
}
