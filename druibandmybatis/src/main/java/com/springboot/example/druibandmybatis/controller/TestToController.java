package com.springboot.example.druibandmybatis.controller;

import com.springboot.example.druibandmybatis.Dao.TextResource;
import com.springboot.example.druibandmybatis.pojo.ListUser;
import com.springboot.example.druibandmybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
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


}
