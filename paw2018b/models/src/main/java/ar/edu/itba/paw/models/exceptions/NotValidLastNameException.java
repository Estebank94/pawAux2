package ar.edu.itba.paw.models.exceptions;

public class NotValidLastNameException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongLastName";
    private String debugMessage = "Wrong Lastname Input";

    public NotValidLastNameException() {
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

    public NotValidLastNameException(String message) {
        super(message);
    }
}
