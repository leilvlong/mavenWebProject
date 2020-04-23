package com.springboot.example.druibandmybatis.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.springboot.example.druibandmybatis.Dao.TextResource;
import com.springboot.example.druibandmybatis.annotations.AffairManage;
import com.springboot.example.druibandmybatis.pojo.ListUser;
import com.springboot.example.druibandmybatis.pojo.NewUser;
import com.springboot.example.druibandmybatis.pojo.User;
import com.springboot.example.druibandmybatis.utils.ConnectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Autowired
    ConnectionUtils dataSource;

    @AffairManage
    public void testAffairManage() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update user set password = 123 where id = 15");
        preparedStatement.execute();

        int i = 10/0;

        PreparedStatement statement = connection.prepareStatement("update user set password = 123 where id = 19");
        statement.execute();
        connection.close();

    }


    public static void main(String[] args) throws IOException {

        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        stringThreadLocal.remove();

    }



}
