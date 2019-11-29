package ar.edu.itba.paw.models.exceptions;

public class NotValidCertificateException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongCertificate";
    private String debugMessage = "Wrong Certificate Input";

    public NotValidCertificateException() {
        super();
    }

    @Override
    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String debugMessage() {
        return debugMessage;
    }

    public NotValidCertificateException(String message) {
        super(message);
    }
}
