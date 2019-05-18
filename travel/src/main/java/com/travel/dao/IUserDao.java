package com.travel.dao;

import com.travel.domain.User;

public interface IUserDao {
    User findUserByUsername(String username);

    boolean registerUser(User user);

    User userActive(String code);

    boolean updateUser(User user);
}
