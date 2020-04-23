package com.springboot.example.druibandmybatis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.example.druibandmybatis.pojo.ListUser;
import com.springboot.example.druibandmybatis.pojo.NewUser;
import com.springboot.example.druibandmybatis.pojo.User;
import com.springboot.example.druibandmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class TestToController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(Model model){
        return "views/index";
    }

    @RequestMapping("/changeLang")
    @ResponseBody
    public String changeLang(String lang,HttpServletRequest request){

        if ("zh".equals(lang)){
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,Locale.CHINESE);
        }else if("en".equals(lang)){
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,Locale.ENGLISH);
        }else{
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
        }
        return "ok";
    }

    @RequestMapping("/queryOneToOne")
    @ResponseBody
    public User queryOneToOne(Long age){
        return userService.findAllUser(age);
    }

    @RequestMapping("/queryOneToMan")
    @ResponseBody
    public List<ListUser> query(Long age){
        return userService.findListUser(age);
    }

    @RequestMapping("/user")
    @ResponseBody
    public String user() throws JsonProcessingException {
        NewUser newUser = new NewUser();
        newUser.setUsername("new");
        newUser.setPassword("123");
        newUser.setAge(1);
        newUser.setEmail("123@456");

        User user = new User();
        user.setId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(user);

        newUser.setSex(value);

        userService.insertUser(newUser);
        return "ok";
    }

    @RequestMapping("/affair")
    @ResponseBody
    public String affair() throws  SQLException {
        userService.testAffairManage();
        return "ok";
    }


}
