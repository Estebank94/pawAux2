package ar.edu.itba.paw.models.exceptions;

public class NotValidPhoneNumberException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongPhoneNumber";
    private String debugMessage = "Wrobg Phone Number Input";

    public NotValidPhoneNumberException() {
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

    public NotValidPhoneNumberException(String message) {
        super(message);
    }
}
