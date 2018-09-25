package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

public interface UserDao {

    User createUser(Integer id, String password, String email, String role);

}
