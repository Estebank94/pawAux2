package ar.edu.itba.paw.models.exceptions;

public class NotValidWorkingHourException extends Exception implements ExceptionWithAttributeName{

    private String attributeName = "wrongWorkingHour";
    private String debugMessage = "Wrong WorkingHour Input";

    public NotValidWorkingHourException() {
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

    public NotValidWorkingHourException(String message) {
        super(message);
    }
}
