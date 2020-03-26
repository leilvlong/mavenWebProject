package com.example.demo.dservice.impl;

import com.example.demo.annotations.Message_Type;
import com.example.demo.dservice.IMessageService;
import com.example.demo.enums.MessageType;
import com.example.demo.parmters.MessageInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

@Component("pictureMessageService")
@Message_Type(MessageType.PICTURE)
public class PictureMessageServiceImpl implements IMessageService {

    @Override
    public Boolean operationMessage(MessageInfo messageInfo) {
        System.out.println(messageInfo.getMessage()+" pictureMessageService");
        return true;
    }
}
