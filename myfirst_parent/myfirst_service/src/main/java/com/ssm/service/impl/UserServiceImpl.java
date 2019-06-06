package com.ssm.service.impl;

import com.ssm.dao.UserDao;
import com.ssm.daomain.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User findIdByUser(Integer id) {
        return userDao.findIdByUser(id);
    }

    public void updateUser(){
        User user1 = new User();
        User user2 = new User();

        user1.setId(1);
        user1.setPassword("333333");
        user2.setId(9);
        user2.setPassword("999999");

        userDao.updateByUser(user1);
        userDao.updateByUser(user2);
    }

}
