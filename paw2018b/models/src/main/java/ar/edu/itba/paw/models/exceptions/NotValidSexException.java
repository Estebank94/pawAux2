package ar.edu.itba.paw.models.exceptions;

public class NotValidSexException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongSex";

    public NotValidSexException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String debugMessage() {
        return null;
    }

    public NotValidSexException(String message) {
        super(message);
    }
}
