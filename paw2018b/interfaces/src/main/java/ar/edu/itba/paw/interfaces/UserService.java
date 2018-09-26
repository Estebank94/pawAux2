package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

public interface UserService {

	public User findById(Integer id);

	public User createUser(Integer id, String password, String email, String role);
}
