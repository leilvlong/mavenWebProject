package com.example.demo.controller;

import com.example.demo.annotations.OpLog;
import com.example.demo.components.MessageInfoServiceContext;
import com.example.demo.dservice.IMessageService;
import com.example.demo.parmters.MessageInfo;
import com.example.demo.responses.ErrorMsssage;
import com.example.demo.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 策略模式以及aop+注解验证参数
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
    public Response validator(@RequestBody MessageInfo messageInfo, ErrorMsssage errorMsssage){

        return new Response(200,errorMsssage.getErrorMessage());
    }
}
