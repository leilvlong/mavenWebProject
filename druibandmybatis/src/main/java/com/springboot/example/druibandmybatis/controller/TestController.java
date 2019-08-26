package com.springboot.example.druibandmybatis.controller;


import com.springboot.example.druibandmybatis.Dao.UserDao;
import com.springboot.example.druibandmybatis.pojo.User;
import com.springboot.example.druibandmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class TestController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Value("${name.username}")
    private String username;

    @RequestMapping("/hi")
    public String hi(){
        return "hi: "+ username;
    }

    @RequestMapping("/hello")
    public List<User> AllUser(){
        return userDao.findAll();
    }

    @RequestMapping("/tran")
    public String insetrUser(){
        try {
            if (userService != null){
                userService.insetrUser(null);
                System.out.println("插入user 测试事务");
            }
            return "ok";
        }catch (Exception e){
            return "no ok";
        }
    }

}
