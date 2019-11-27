package ar.edu.itba.paw.models.exceptions;

public class NotValidPasswordException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongPassword";

    public NotValidPasswordException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidPasswordException(String message) {
        super(message);
    }
}