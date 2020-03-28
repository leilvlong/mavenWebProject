package com.example.demo.aop;

import com.example.demo.annotations.ValiDator;
import com.example.demo.responses.ErrorMsssage;
import com.example.demo.annotations.Max;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class validatorAop {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void validatorAopApi(){
    }

    @Before("validatorAopApi()")
    public void doBefore(JoinPoint joinPoint) throws IllegalAccessException {
        StringBuilder message = new StringBuilder();
        ErrorMsssage errorMsssage = null;
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {

            if (arg instanceof ErrorMsssage){
                errorMsssage = (ErrorMsssage) arg;
                continue;
            }
            Field[] argFields = arg.getClass().getDeclaredFields();
            for (Field argField : argFields) {
                argField.setAccessible(true);
                Annotation[] argAnnotations = argField.getAnnotations();
                for (Annotation argAnnotation : argAnnotations) {
                    if (argAnnotation instanceof Max){
                        int annotationValue = ((Max) argAnnotation).value();
                        int argValue = (int) argField.get(arg);
                        if (annotationValue<argValue){
                            message.append(argField.getName()) .append( ": Over the maximum");
                        }
                    }
                }
            }
        }
        errorMsssage.setErrorMessage(message.toString());
    }


}
