package com.spring;

import com.spring.config.AppConfig;
import com.spring.dao.impl.UserDaoImpl;
import com.spring.domain.User;
import com.spring.service.UserService;
import com.spring.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService1 =  ac.getBean("userService",UserService.class);


        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(9);
        userService1.updateByUser(user1,user2);
    }
}
