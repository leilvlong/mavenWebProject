package com.spring.service.impl;

import com.spring.dao.UserDao;
import com.spring.domain.User;
import com.spring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
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
    @Transactional(value = "DataSourceTransactionManager")
    public void updateByUser(User forUser, User toUser) {
        forUser.setPassword("333333");
        toUser.setPassword("666666");

        ud.updateByUser(forUser);
        int i = 10 / 0;
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
