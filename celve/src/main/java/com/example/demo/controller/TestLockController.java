package com.example.demo.controller;


import com.example.demo.annotations.Lock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestLockController {

    @RequestMapping("/lock")
    @Lock(key = "lock", timeOut = 3000L)
    @ResponseBody
    public String lock(){

        System.out.println("hello lock");

        return "ok";
    }
}
