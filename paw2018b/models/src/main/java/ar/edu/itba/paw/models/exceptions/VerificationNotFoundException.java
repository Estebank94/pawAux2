package ar.edu.itba.paw.models.exceptions;

import javassist.NotFoundException;

@SuppressWarnings("serial")
public class VerificationNotFoundException extends NotFoundException {

    public VerificationNotFoundException() {
        super("VerificationNotFound");
    }
}