package ar.edu.itba.paw.services;

import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Service
public class AnotherUserServiceImpl implements UserService {

	@Override
	public User findById(Integer id) {
		return null;
	}
}
