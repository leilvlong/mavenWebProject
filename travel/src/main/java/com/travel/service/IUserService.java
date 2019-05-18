package com.travel.service;

import com.travel.domain.User;
import com.travel.exception.UserActiveError;
import com.travel.exception.UserPasswordError;
import com.travel.exception.UserUsernameError;

public interface IUserService {
    User findUserByUsername(String username);
    boolean registerUser(User user);

    void userActive(String code);

    User userLogin(String username, String password) throws UserActiveError, UserPasswordError, UserUsernameError;
}
