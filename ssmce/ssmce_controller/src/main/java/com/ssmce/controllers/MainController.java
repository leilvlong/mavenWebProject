package com.ssmce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class MainController {

    @RequestMapping("/{main}")
    public String mainJsp(@PathVariable String main){

        return main+".jsp";
    }


}
