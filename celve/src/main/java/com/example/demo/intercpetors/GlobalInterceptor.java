package com.example.demo.intercpetors;

import com.example.demo.dservice.IMessageService;
import com.example.demo.dservice.impl.PictureMessageServiceImpl;
import com.example.demo.parmters.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class GlobalInterceptor implements HandlerInterceptor {

    @Autowired
    IMessageService pictureMessageService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("HandlerInterceptor------start------HandlerInterceptor");
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessage("hello");
        messageInfo.setMessageType(1);

        pictureMessageService.operationMessage(messageInfo);

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println("parameterName:" + parameterNames.nextElement());
        }
        System.out.println("HandlerInterceptor------end------HandlerInterceptor");
        System.out.println();
        return true;
    }
}
