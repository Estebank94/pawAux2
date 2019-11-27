package ar.edu.itba.paw.models.exceptions;

public class NotValidLicenceException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongLicence";

    public NotValidLicenceException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public NotValidLicenceException(String message) {
        super(message);
    }
}
