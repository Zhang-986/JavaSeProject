package com.campus.dao;

import com.campus.entity.User;

public interface UserDao {
    User findByUsername(String username);
    boolean save(User user);

} 