package ar.edu.itba.paw.models.exceptions;

public class NotValidLastNameException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongLastName";

    public NotValidLastNameException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidLastNameException(String message) {
        super(message);
    }
}
