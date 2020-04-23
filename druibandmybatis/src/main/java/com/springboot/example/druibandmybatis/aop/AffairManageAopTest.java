package com.springboot.example.druibandmybatis.aop;





import com.springboot.example.druibandmybatis.utils.ConnectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
@Aspect
@Component
public class AffairManageAopTest {

    @Pointcut("@annotation(com.springboot.example.druibandmybatis.annotations.AffairManage)")
    public void pointcut(){}

    @Autowired
    ConnectionUtils connectionUtils;

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Connection connection = null;
        Object proceed = null;
        try {
            connection = connectionUtils.getConnection();
            connection.setAutoCommit(false);
            proceed = joinPoint.proceed();
            connection.commit();
        }catch (Exception e){
            if (connection != null){
                connection.rollback();
            }
        }finally {
            if (connection != null){
                connectionUtils.removeConnection();
            }
        }

        return proceed;

    }

}


