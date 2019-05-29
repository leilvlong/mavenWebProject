package com.spring.config;


import com.spring.util.JdbcUtil;
import com.spring.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Bean(name="dataSource")
    public DataSource createDataSourec(){
        return JdbcUtil.getDataSource();
    }

    @Bean(name="jdbc")
    @Scope("prototype")
    @Qualifier("dataSource")
    public JdbcTemplate createJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="TransactionUtil")
    @Scope("prototype")
    public TransactionUtil createTransactionUtil(){
        return new TransactionUtil();
    }

    @Bean(name="DataSourceTransactionManager")
    @Scope("prototype")
    @Qualifier("dataSource")
    public DataSourceTransactionManager creDataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
