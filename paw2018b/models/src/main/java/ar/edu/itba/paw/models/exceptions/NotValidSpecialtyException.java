package ar.edu.itba.paw.models.exceptions;

public class NotValidSpecialtyException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongSpecialty";
    private String debugMessage = "Wrong specialty Input";

    public NotValidSpecialtyException() {
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

    public NotValidSpecialtyException(String message) {
        super(message);
    }
}
