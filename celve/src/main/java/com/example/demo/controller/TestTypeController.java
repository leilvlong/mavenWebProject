package com.example.demo.controller;

import com.example.demo.annotations.OpLog;
import com.example.demo.annotations.ValiDator;
import com.example.demo.components.MessageInfoServiceContext;
import com.example.demo.dservice.IMessageService;
import com.example.demo.parmters.MessageInfo;
import com.example.demo.responses.ErrorMsssage;
import com.example.demo.responses.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestTypeController {

    @Autowired
    private MessageInfoServiceContext messageInfoServiceContext;

    @PostMapping("/type")
    @OpLog(value = "testOpLog")
    public TypeResponse operationMessage(@RequestBody MessageInfo messageInfo ){
        IMessageService messageService = messageInfoServiceContext.getMessageService(messageInfo.getMessageType());
        messageService.operationMessage(messageInfo);
        int a = 3;
        if (a == 3){
            throw new RuntimeException("test exception");
        }
        return new TypeResponse(200,"OK");
    }

    @PostMapping("/validator")
    public TypeResponse validator(@RequestBody MessageInfo messageInfo, ErrorMsssage errorMsssage){

        return new TypeResponse(200,errorMsssage.getErrorMessage());
    }
}
