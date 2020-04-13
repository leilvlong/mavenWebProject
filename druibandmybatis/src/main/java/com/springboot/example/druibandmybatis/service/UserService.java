package com.springboot.example.druibandmybatis.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.springboot.example.druibandmybatis.Dao.TextResource;
import com.springboot.example.druibandmybatis.pojo.ListUser;
import com.springboot.example.druibandmybatis.pojo.NewUser;
import com.springboot.example.druibandmybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        return user;
    }

    public List<ListUser> findListUser(Long age){
        List<ListUser> listUsers = textResource.findListUser(age);

        return getListUser(listUsers);
    }

    public List<ListUser> getListUser(List<ListUser> listUsers){
        for (ListUser listUser : listUsers) {
            List<ListUser> users = textResource.findListUser(listUser.getId());
            listUser.setUser(users);
            if (!users.isEmpty()){
                getListUser(users);
            }
        }


        return listUsers;
    }

    public Boolean insertUser(NewUser newUser){
        int insert = textResource.insertSelective(newUser);
        return true;
    }


    public static void main(String[] args) throws IOException {



    }



}
