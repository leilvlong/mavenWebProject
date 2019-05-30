package com.spring.util;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import javax.annotation.Resource;

/*@Aspect
@Component
public class TransactionUtil {

    @Resource(name="DataSourceTransactionManager")
    private DataSourceTransactionManager dtm;

    private TransactionStatus status;


    @Pointcut("execution(* com.spring.service.UserService.*(..))")
    public void myTransactionUtil(){}


    @Before("myTransactionUtil()")
    public  void startAffair(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        status = dtm.getTransaction(def);
    }

    @AfterReturning("myTransactionUtil()")
    public void commit(){
        dtm.commit(status);
    }

    @AfterThrowing("myTransactionUtil()")
    public void rollback(){
        dtm.rollback(status);
    }

}*/
