package com.campus.service.impl;

import com.campus.dao.UserDao;
import com.campus.dao.impl.UserDaoImpl;
import com.campus.entity.User;
import com.campus.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(String username, String password) {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username, password, "user");
        return userDao.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

} 