package com.spring.service.impl;

import com.spring.dao.UserDao;
import com.spring.domain.User;
import com.spring.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao ud;

    public void setUd(UserDao ud) {
        this.ud = ud;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void delByUidUser(Integer uid) {

    }

    @Override
    public void updateByUid(Integer uid) {

    }

    @Override
    public User findUidByUser(Integer uid) {


        return ud.findUidByUser(uid);
    }

    @Override
    public List<User> finAllUser() {
        return null;
    }
}
