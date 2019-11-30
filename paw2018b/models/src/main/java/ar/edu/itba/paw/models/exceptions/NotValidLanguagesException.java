package ar.edu.itba.paw.models.exceptions;

public class NotValidLanguagesException extends Exception  implements ExceptionWithAttributeName{

    private String attributeName = "wrongLanguage";
    private String debugMessage = "Wrong Language Input";

    public NotValidLanguagesException() {
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

    public NotValidLanguagesException(String message) {
        super(message);
    }
}
