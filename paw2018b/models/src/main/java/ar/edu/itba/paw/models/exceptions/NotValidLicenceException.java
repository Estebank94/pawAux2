package ar.edu.itba.paw.models.exceptions;

public class NotValidLicenceException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongLicence";
    private String debugMessage = "Wrong Licence Input";

    public NotValidLicenceException() {
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

    public NotValidLicenceException(String message) {
        super(message);
    }
}
