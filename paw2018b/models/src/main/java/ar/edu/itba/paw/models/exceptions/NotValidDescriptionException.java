package ar.edu.itba.paw.models.exceptions;

public class NotValidDescriptionException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongDesciption";
    private String debugMessage = "Wrong Description Input";

    public NotValidDescriptionException() {
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

    public NotValidDescriptionException(String message) {
        super(message);
    }
}
