package ar.edu.itba.paw.models.exceptions;

public class RepeatedEmailException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "repeatedEmail";
    private String debugMessage = "Repeated Email Address";

    public RepeatedEmailException() {
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

    public RepeatedEmailException(String message) {
        super(message);
    }
}
