package ar.edu.itba.paw.models.exceptions;

public class NotValidSexException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongSex";
    private String debugMessage = "Wrong Sex Input";

    public NotValidSexException() {
        super();
    }

    @Override
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
