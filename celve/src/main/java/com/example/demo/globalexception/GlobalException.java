package com.example.demo.globalexception;

import com.example.demo.responses.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e){
        System.out.println("GlobalException------start------GlobalException");
        Response typeResponse = new Response();
        typeResponse.setStatus(500);
        typeResponse.setMessage(e.getMessage());
        System.out.println("GlobalException------end------GlobalException");
        System.out.println();
        return typeResponse;
    }

}
