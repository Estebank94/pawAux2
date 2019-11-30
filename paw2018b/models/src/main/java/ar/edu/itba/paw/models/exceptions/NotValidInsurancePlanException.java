package ar.edu.itba.paw.models.exceptions;

public class NotValidInsurancePlanException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongInsurancePlan";
    private String debugMessage = "Wrong InsurancePlan Input";

    public NotValidInsurancePlanException() {
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

    public NotValidInsurancePlanException(String message) {
        super(message);
    }
}
