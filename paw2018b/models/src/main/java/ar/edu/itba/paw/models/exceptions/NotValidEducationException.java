package ar.edu.itba.paw.models.exceptions;

public class NotValidEducationException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongEducation";
    private String debugMessage = "Wrong education Input";

    public NotValidEducationException() {
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

    public NotValidEducationException(String message) {
        super(message);
    }
}
