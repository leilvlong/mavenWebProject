package com.springcloud.demo.controller;

import com.springcloud.demo.pojo.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class TestDecrypt {

    @Value("${pass}")
    private String pass;

    @RequestMapping("/test/decrypt")
    public Response testDecrypt(){
        return new Response(pass,200);
    }

}
