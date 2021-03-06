package com.example.demo.dservice.impl;

import com.example.demo.annotations.Message_Type;
import com.example.demo.dservice.IMessageService;
import com.example.demo.enums.MessageType;
import com.example.demo.parmters.MessageInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("textMessageServiceImpl")
@Message_Type(MessageType.TEXT)
public class TextMessageServiceImpl implements IMessageService {
    @Override
    public Boolean operationMessage(MessageInfo messageInfo) {
        System.out.println(messageInfo.getMessage()+" textMessageServiceImpl");
        return true;
    }
}
