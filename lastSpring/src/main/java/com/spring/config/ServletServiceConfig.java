package com.spring.config;


import com.spring.service.UserService;
import com.spring.service.handler.UserServiceHandler;
import com.spring.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

/*@Configuration
public class ServletServiceConfig {

    @Bean(name="userServiceImpl")
    public UserServiceImpl creDataSourceTransactionManager(){
        return new UserServiceImpl();
    }

    @Bean(name="userServiceHandler")
    public UserServiceHandler createUserServiceHandler(){
        return new UserServiceHandler();
    }

    @Bean(name="userService")
    @Qualifier("userServiceHandler")
    public UserService createUserService (UserServiceHandler userServiceHandler){
        return (UserService) Proxy.newProxyInstance(UserServiceHandler.class.getClassLoader(),
                new Class[]{UserService.class},
                userServiceHandler
                );
    }
}*/
