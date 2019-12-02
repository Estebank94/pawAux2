package ar.edu.itba.paw.webapp.exceptionmapper;

public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}
