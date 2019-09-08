package com.pingyougou.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/getUserName")
    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
