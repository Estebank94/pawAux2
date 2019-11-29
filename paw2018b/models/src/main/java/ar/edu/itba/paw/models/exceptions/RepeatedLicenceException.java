package ar.edu.itba.paw.models.exceptions;

public class RepeatedLicenceException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "repeatedLicence";
    private String debugMessage = "Repeated Licence Input";

    public RepeatedLicenceException() {
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

    public RepeatedLicenceException(String message) {
        super(message);
    }
}
