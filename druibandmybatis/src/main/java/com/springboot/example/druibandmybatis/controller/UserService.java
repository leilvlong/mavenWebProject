package com.springboot.example.druibandmybatis.controller;

import com.springboot.example.druibandmybatis.Dao.TextResource;
import com.springboot.example.druibandmybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    TextResource textResource;

    public User findAllUser(Long age){
        User allUser = textResource.findAllUser(age);
        return getUser(allUser);
    }

    public User getUser(User user){
        User allUser = textResource.findAllUser(user.getId());
        user.setUser(allUser);
        if (allUser != null){
            getUser(allUser);
        }

        return allUser;
    }

}
