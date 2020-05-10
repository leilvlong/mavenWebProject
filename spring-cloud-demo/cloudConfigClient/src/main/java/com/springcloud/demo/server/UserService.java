package com.springcloud.demo.server;

import com.springcloud.demo.dao.Test1Dao;
import com.springcloud.demo.dao.Test2Dao;
import com.springcloud.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private Test1Dao userMapper1;
    @Autowired
    private Test2Dao userMapper2;

    @Transactional
    public void addUser(User user1, User user2)throws Exception{
        userMapper1.addUser(user1.getName(),user1.getAge());
        int i = 10/0;

        userMapper2.addUser(user2.getName(),user2.getAge());
    }
}

