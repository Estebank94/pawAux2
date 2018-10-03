package ar.edu.itba.paw.models.exceptions;

public class NotValidCertificateException extends Exception {
    public NotValidCertificateException() {
        super();
    }

    public NotValidCertificateException(String message) {
        super(message);
    }
}
