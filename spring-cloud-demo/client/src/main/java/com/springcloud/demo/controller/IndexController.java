package com.springcloud.demo.controller;


import com.springcloud.demo.pojo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/index/client")
    public Response index(){
        throw new RuntimeException();
        //return new Response("index client ok", 200);
    }
}
