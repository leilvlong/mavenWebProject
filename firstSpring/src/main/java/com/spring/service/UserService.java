package com.spring.service;

import com.spring.domain.User;

import java.util.List;

public interface UserService {
    public void addUser(User user);

    public void delByUidUser(Integer uid);

    public void updateByUid(Integer uid);

    public void updateByUser(User forUser, User toUser);

    public User findUidByUser(Integer uid);

    public List<User> finAllUser();
}
