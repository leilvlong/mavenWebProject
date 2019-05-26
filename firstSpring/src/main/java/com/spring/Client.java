package com.spring;

import com.spring.domain.User;
import com.spring.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("springConfig.xml");

        UserServiceImpl us = ac.getBean("userService", UserServiceImpl.class);

        User uidByUser = us.findUidByUser(1);

        System.out.println(uidByUser);
    }
}
