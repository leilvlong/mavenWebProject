package com.spring.util;

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


public class TransactionUtil {

    @Resource(name="DataSourceTransactionManager")
    private DataSourceTransactionManager dtm;
    public void setDtm(DataSourceTransactionManager dtm) {
        this.dtm = dtm;
    }

    private TransactionStatus status;

    public  void startAffair(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        status = dtm.getTransaction(def);
    }

    public void commit(){
        dtm.commit(status);
    }

    public void rollback(){
        dtm.rollback(status);
    }

}
