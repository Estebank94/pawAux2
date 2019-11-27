package ar.edu.itba.paw.models.exceptions;

public class NotValidEmailException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongEmail";

    public NotValidEmailException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidEmailException(String message) {
        super(message);
    }
}
