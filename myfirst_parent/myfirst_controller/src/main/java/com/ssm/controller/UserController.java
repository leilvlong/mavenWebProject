package com.ssm.controller;


import com.ssm.daomain.User;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ExceptionHandler
    public String eror (Exception e){
        return "error.jsp";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @RequestMapping("/get/{id}")
    public String findIdByUser(Model model, @PathVariable Integer id){
        User idByUser = userService.findIdByUser(id);
        model.addAttribute(idByUser);
        if (true){
            throw new RuntimeException();
        }
        return "test.jsp";
    }

    @RequestMapping("/upd")
    public String updateUser(){
        userService.updateUser();
        return "test.jsp";
    }

    @PostMapping("/date")
    public String date( User user ){
        System.out.println("执行");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());
        System.out.println(user.getDate());
        return "test.jsp";
    }

    @RequestMapping("/test")
    public String test(){
        return "test.jsp";
    }

}
