package ar.edu.itba.paw.models.exceptions;

public class NotValidAddressException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongAddress";

    public NotValidAddressException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidAddressException(String message) {
        super(message);
    }
}
