package com.example.demo.dservice.impl;

import com.example.demo.annotations.Message_Type;
import com.example.demo.dservice.IMessageService;
import com.example.demo.enums.MessageType;
import com.example.demo.parmters.MessageInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("videoMessageServiceImpl")
@Message_Type(MessageType.VIDEO)
public class VideoMessageServiceImpl implements IMessageService {

    @Override
    public Boolean operationMessage(MessageInfo messageInfo) {
        System.out.println(messageInfo.getMessage()+" videoMessageServiceImpl");
        return true;
    }
}
