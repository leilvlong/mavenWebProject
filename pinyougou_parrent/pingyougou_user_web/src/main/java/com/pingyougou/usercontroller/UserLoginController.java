package com.pingyougou.usercontroller;


import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class UserLoginController {


    @RequestMapping("/getName")
    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
