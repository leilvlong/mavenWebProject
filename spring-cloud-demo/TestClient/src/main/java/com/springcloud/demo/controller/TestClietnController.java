package com.springcloud.demo.controller;

import com.springcloud.demo.openfeign.IndexClient;
import com.springcloud.demo.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestClietnController {

    @Autowired
    IndexClient indexClient;

    @RequestMapping("/test/client")
    public Response testClient(){
        return new Response("test client ok", 200);
    }

    @RequestMapping("/test/index")
    public Response testIndex(){
        return indexClient.index();
    }

}
