package ar.edu.itba.paw.models.exceptions;

public class NotValidFirstNameException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongFirstName";

    public NotValidFirstNameException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidFirstNameException(String message) {
        super(message);
    }
}
