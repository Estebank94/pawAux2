package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(Integer id) {
        return userDao.findUserById(id).get();
    }

    @Override
    public User createUser(Integer id, String password, String email, String role) {
        return userDao.createUser(id, password, email, role);
    }

}
