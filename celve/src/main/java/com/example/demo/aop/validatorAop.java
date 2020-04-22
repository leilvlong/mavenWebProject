package com.example.demo.aop;

import com.example.demo.annotations.ValiDator;
import com.example.demo.components.ValidatorListener;
import com.example.demo.dservice.IParameterDisposeService;
import com.example.demo.enums.ParameterTypes;
import com.example.demo.responses.ErrorMsssage;
import com.example.demo.annotations.Max;
import com.example.demo.responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sun.plugin.com.ParameterListCorrelator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;

@Aspect
@Component
@Order(5)
public class validatorAop {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void validatorAopApi(){
    }

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    ValidatorListener validatorListener;

    @Around("validatorAopApi()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("parameterCheck------start------parameterCheck");


        long start = System.currentTimeMillis();

        Object[] args = joinPoint.getArgs();
        if (args.length == 0){
            System.out.println("parameterCheck------end------parameterCheck");
            return joinPoint.proceed();
        }

        ErrorMsssage errorMsssage = null;
        LinkedList<String> errorMessageList = new LinkedList<>();;

        StringBuilder message = new StringBuilder();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            if (arg instanceof ErrorMsssage){
                errorMsssage = (ErrorMsssage) arg;
                continue;
            }

            Annotation[] parameterAnnotation = parameterAnnotations[i];

            if (parameterAnnotation.length == 0) {
                continue;
            }

            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof ValiDator){

                    Field[] argFields = arg.getClass().getDeclaredFields();

                    for (Field argField : argFields) {

                        argField.setAccessible(true);
                        Annotation[] argAnnotations = argField.getAnnotations();

                        for (Annotation argAnnotation : argAnnotations) {
                            if (argAnnotation instanceof Max){

                                Max maxArgAnnotation= (Max) argAnnotation;
                                int annotationValue = maxArgAnnotation.value();
                                boolean requisite = maxArgAnnotation.requisite();
                                Object argValue = argField.get(arg);

                                if (argValue != null){
                                    IParameterDisposeService iParameterDisposeService;

                                    if (argValue instanceof Integer){
                                        iParameterDisposeService = validatorListener.getIParameterDisposeService(ParameterTypes.Integer.getType());
                                        iParameterDisposeService.disposeParameter(errorMessageList, argValue, annotationValue, message, argField);
                                    }

                                    if (argValue instanceof String){
                                        iParameterDisposeService = validatorListener.getIParameterDisposeService(ParameterTypes.String.getType());
                                        iParameterDisposeService.disposeParameter(errorMessageList, argValue, annotationValue, message, argField);
                                    }

                                }else{
                                    if (requisite){
                                        message.append(argField.getName()) .append( ": parameter is Null");
                                        errorMessageList.add(message.toString());
                                        message.delete(0, message.length());
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        if (errorMsssage != null && !errorMessageList.isEmpty()){
            errorMsssage.setErrorMessage(errorMessageList);
            Response response = new Response();
            response.setStatus(500);
            response.setMessage(errorMsssage.getErrorMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(response);


            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            try{
                outputStream.write(value.getBytes());
            }finally {
                outputStream.flush();
                outputStream.close();
            }

        }

        long end = System.currentTimeMillis();
        long over = end - start;
        System.out.println("执行验证参数时间(毫秒为单位):  " +  over);
        System.out.println("parameterCheck------end------parameterCheck");
        System.out.println();
        return joinPoint.proceed();

    }
}
