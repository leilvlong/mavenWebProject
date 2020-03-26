package com.example.demo.components;

import com.example.demo.annotations.Message_Type;
import com.example.demo.dservice.IMessageService;
import com.example.demo.enums.MessageType;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageInfoListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> beansWithAnnotation = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(Message_Type.class);
        MessageInfoServiceContext messageInfoServiceContext = contextRefreshedEvent.getApplicationContext().getBean(MessageInfoServiceContext.class);
        beansWithAnnotation.forEach((key,value)->{
            MessageType messageType = value.getClass().getAnnotation(Message_Type.class).value();
            messageInfoServiceContext.setMessageServiceInfo(messageType.getType(), (IMessageService) value);
        });

    }
}
