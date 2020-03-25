package com.example.demo.components;

import com.example.demo.dservice.IMessageService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageInfoServiceContext {

    private final Map<Integer, IMessageService> messageServiceInfoMap = new HashMap<>();

    public IMessageService getMessageService(Integer type){
        return messageServiceInfoMap.get(type);
    }

    public void setMessageServiceInfo(Integer type, IMessageService messageService){
        messageServiceInfoMap.put(type, messageService);
    }
}
