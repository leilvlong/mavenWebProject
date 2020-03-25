package com.example.demo.aop;

import com.example.demo.annotations.Message_Type;
import com.example.demo.annotations.OpLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class opLogAop {

    @Pointcut("@annotation(com.example.demo.annotations.OpLog)")
    public void opLogAopApi(){
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Before("opLogAopApi() && @annotation(opLog)")
    public void doBeforeGame( OpLog opLog){
        System.out.println(opLog.value());
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Message_Type.class);
        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            System.out.println(stringObjectEntry.getKey());
            System.out.println(stringObjectEntry.getValue());
        }
    }

}
