package ar.edu.itba.paw.models.exceptions;

public class NotValidAddressException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongAddress";
    private String debugMessage = "Wrong Address Input";

    public NotValidAddressException() {
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

    public NotValidAddressException(String message) {
        super(message);
    }
}
