package ar.edu.itba.paw.models.exceptions;

public class RepeatedEmailException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "repeatedEmail";

    public RepeatedEmailException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public RepeatedEmailException(String message) {
        super(message);
    }
}
