package ar.edu.itba.paw.webapp.exceptionmapper;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException() {
        super("User not Found");
    }
}
