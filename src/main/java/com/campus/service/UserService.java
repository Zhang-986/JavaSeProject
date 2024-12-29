package com.campus.service;

import com.campus.entity.User;

public interface UserService {
    boolean register(String username, String password);
    User login(String username, String password);

} 