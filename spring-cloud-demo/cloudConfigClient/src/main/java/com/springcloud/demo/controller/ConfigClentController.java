package com.springcloud.demo.controller;

import com.springcloud.demo.pojo.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ConfigClentController {

    @Value("${word}")
    private String world;

    @Value("pass")
    private String pass;

    @RequestMapping("/configclent")
    public Response configClent(){
        return new Response(world + ": " + pass,200);
    }
}
