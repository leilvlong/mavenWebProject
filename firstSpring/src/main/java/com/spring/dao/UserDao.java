package com.spring.dao;

import com.spring.domain.User;

import java.util.List;

public interface UserDao {
    public void addUser(User user);

    public void delByUidUser(Integer uid);

    public void updateByUid(Integer uid);
    public void updateByUser(User User);

    public User findUidByUser(Integer uid);

    public List<User> finAllUser();
}
