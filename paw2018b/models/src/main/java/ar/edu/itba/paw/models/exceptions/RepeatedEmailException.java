package ar.edu.itba.paw.models.exceptions;

public class RepeatedEmailException extends Exception{
    public RepeatedEmailException() {
        super();
    }

    public RepeatedEmailException(String message) {
        super(message);
    }
}
