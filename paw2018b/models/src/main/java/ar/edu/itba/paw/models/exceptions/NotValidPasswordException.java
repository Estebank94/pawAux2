package ar.edu.itba.paw.models.exceptions;

public class NotValidPasswordException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongPassword";
    private String debugMessage = "Wrobg Password Input";

    public NotValidPasswordException() {
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

    public NotValidPasswordException(String message) {
        super(message);
    }
}