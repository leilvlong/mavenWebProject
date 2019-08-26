package com.springboot.example.druibandmybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestToController {

    @RequestMapping("/hi")
    public String hi(Model model){
        model.addAttribute("key","value" );
        return "views/test";
    }

}
