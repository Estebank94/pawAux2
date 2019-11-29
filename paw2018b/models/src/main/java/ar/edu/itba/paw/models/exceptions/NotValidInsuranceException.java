package ar.edu.itba.paw.models.exceptions;

public class NotValidInsuranceException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongInsurance";
    private String debugMessage = "Wrong Insurance Input";

    public NotValidInsuranceException() {
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

    public NotValidInsuranceException(String message) {
        super(message);
    }
}
