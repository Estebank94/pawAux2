package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserDao {

    User createUser(Integer id, String password, String email, String role);
    Optional<User> findUserById(Integer id);
    Optional<User> findUserByEmail(String email);

}
