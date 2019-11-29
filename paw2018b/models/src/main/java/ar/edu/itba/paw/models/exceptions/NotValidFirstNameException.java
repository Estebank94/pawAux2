package ar.edu.itba.paw.models.exceptions;

public class NotValidFirstNameException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongFirstName";
    private String debugMessage = "Wrong First Name Input";

    public NotValidFirstNameException() {
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

    public NotValidFirstNameException(String message) {
        super(message);
    }
}
