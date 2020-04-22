package com.example.demo.components;


import com.example.demo.annotations.ParameterDispose;
import com.example.demo.dservice.IParameterDisposeService;
import org.hibernate.validator.internal.xml.binding.ParameterType;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ValidatorListener implements ApplicationListener<ContextRefreshedEvent> {

    private Map<String, IParameterDisposeService> disposeServiceMap = new HashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> beansWithAnnotation = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(ParameterDispose.class);
        beansWithAnnotation.forEach((key,value)->{
            ParameterDispose annotation = value.getClass().getAnnotation(ParameterDispose.class);
            String type = annotation.value().getType();
            disposeServiceMap.put(type, (IParameterDisposeService) value);
        });
    }

    public IParameterDisposeService getIParameterDisposeService(String key){
        return disposeServiceMap.get(key);
    }
}
