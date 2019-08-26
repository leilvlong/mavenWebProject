package com.springboot.example.druibandmybatis.service;


import com.springboot.example.druibandmybatis.Dao.UserDao;
import com.springboot.example.druibandmybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void insetrUser(User user){
        User abc1 = new User(0L, "abc1", "123456", 0, null, null);
        userDao.insetrUser(abc1);
        int i = 10/0;

        User abc2 = new User(0L, "abc2", "123456", 0, null, null);
        userDao.insetrUser(abc2);
    }
}
