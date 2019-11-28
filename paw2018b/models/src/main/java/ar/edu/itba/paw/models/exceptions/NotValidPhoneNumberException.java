package ar.edu.itba.paw.models.exceptions;

public class NotValidPhoneNumberException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongPhoneNumber";

    public NotValidPhoneNumberException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidPhoneNumberException(String message) {
        super(message);
    }
}
