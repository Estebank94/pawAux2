package ar.edu.itba.paw.models.exceptions;

public class RepeatedLicenceException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "repeatedLicence";

    public RepeatedLicenceException() {
        super();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public RepeatedLicenceException(String message) {
        super(message);
    }
}
