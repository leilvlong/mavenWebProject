package com.example.demo.controller;

import com.example.demo.annotations.OpLog;
import com.example.demo.annotations.ValiDator;
import com.example.demo.components.MessageInfoServiceContext;
import com.example.demo.dservice.IMessageService;
import com.example.demo.enums.MessageType;
import com.example.demo.parmters.MessageInfo;
import com.example.demo.responses.ErrorMsssage;
import com.example.demo.responses.Response;
import org.hibernate.validator.internal.xml.binding.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;

/**
 * 策略模式以及aop+注解验证参数
 * 一个接口多个实现的注入
 */

@RestController
@RequestMapping("/test")
public class TestTypeController {

    @Autowired
    private MessageInfoServiceContext messageInfoServiceContext;

    @PostMapping("/type")
    @OpLog(value = "testOpLog")
    public Response operationMessage(@RequestBody MessageInfo messageInfo ){
        IMessageService messageService = messageInfoServiceContext.getMessageService(messageInfo.getMessageType());
        messageService.operationMessage(messageInfo);
        int a = 3;
        if (a == 3){
            throw new RuntimeException("test exception");
        }
        return new Response(200,"OK");
    }

    @PostMapping("/validator")
    @OpLog("validator opLog")
    public Response validator( ErrorMsssage errorMsssage,String name, @RequestBody @ValiDator MessageInfo messageInfo){

        return new Response(200,"ok");
    }

    @Autowired
    private IMessageService pictureMessageService;

    @Autowired
    private IMessageService textMessageServiceImpl;

    @Autowired
    private IMessageService videoMessageServiceImpl;

    @RequestMapping("/message")
    public String testMessageType(@RequestBody MessageInfo messageInfo){
        pictureMessageService.operationMessage(messageInfo);
        textMessageServiceImpl.operationMessage(messageInfo);
        videoMessageServiceImpl.operationMessage(messageInfo);
        return "OK";
    }
}
