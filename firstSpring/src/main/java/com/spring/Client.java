package com.spring;

import com.spring.domain.User;
import com.spring.service.UserService;
import com.spring.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("springConfig.xml");

        UserService us = ac.getBean("userService", UserService.class);

        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(9);
        us.updateByUser(user1,user2);
    }
}
