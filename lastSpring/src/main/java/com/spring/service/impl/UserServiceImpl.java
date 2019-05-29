package com.spring.service.impl;

import com.spring.dao.UserDao;
import com.spring.domain.User;
import com.spring.service.UserService;
import com.spring.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource(name="userDao")
    private UserDao ud;

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
    public void updateByUser(User forUser, User toUser) {
        forUser.setPassword("555555");
        toUser.setPassword("333333");

        ud.updateByUser(forUser);
        ud.updateByUser(toUser);


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
