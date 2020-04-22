package com.example.demo.aop;

import com.example.demo.annotations.Message_Type;
import com.example.demo.annotations.OpLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@Order(10)
public class opLogAop {

    @Pointcut("@annotation(com.example.demo.annotations.OpLog)")
    public void opLogAopApi(){
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Before("opLogAopApi() && @annotation(opLog)")
    public void doBefore( OpLog opLog){
        System.out.println("OpLog------Before------OpLog");
        System.out.println(opLog.value());
        System.out.println("OpLog------Before------OpLog");
        System.out.println();

    }

    @After("opLogAopApi() && @annotation(opLog)")
    public void doAfter(OpLog opLog){
        System.out.println("OpLog------After------OpLog");
        System.out.println(opLog.value());
        System.out.println("OpLog------After------OpLog");
        System.out.println();
    }

    @AfterReturning(value = "opLogAopApi() && @annotation(opLog)",returning = "ret")
    public Object doAfterReturning(OpLog opLog, Object ret){
        System.out.println("OpLog------AfterReturning------OpLog");
        System.out.println(opLog.value());
        System.out.println("OpLog------AfterReturning------OpLog");
        System.out.println();
        return ret;
    }

    @AfterThrowing(value = "opLogAopApi() && @annotation(opLog)", throwing = "e")
    public void doAfterThrowing(OpLog opLog, Throwable e){
        System.out.println("OpLog------AfterThrowing------OpLog");
        System.out.println(opLog.value());
        System.out.println(e.getMessage());
        System.out.println("OpLog------AfterThrowing------OpLog");
        System.out.println();
    }

    @Around(value = "opLogAopApi() && @annotation(opLog)")
    public Object doAround(ProceedingJoinPoint pjp, OpLog opLog) throws Throwable {
        System.out.println("OpLog------doAround------OpLog");
        System.out.println(pjp);
        System.out.println(opLog.value());
        System.out.println("OpLog------doAround------OpLog");
        System.out.println();
        return pjp.proceed();
    }


}
