package ar.edu.itba.paw.models.exceptions;

public class NotValidEmailException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongEmail";
    private String debugMessage = "Wrong Email Input";

    public NotValidEmailException() {
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

    public NotValidEmailException(String message) {
        super(message);
    }
}
