package com.example.demo.globalexception;

import com.example.demo.responses.TypeResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public TypeResponse exceptionHandler(Exception e){
        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setStatus(500);
        typeResponse.setMessage(e.getMessage());
        return typeResponse;
    }

}
