package com.springboot.example.druibandmybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestToController {

    @RequestMapping("/")
    public String index(){
        return "views/index";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "views/hello";
    }

}
