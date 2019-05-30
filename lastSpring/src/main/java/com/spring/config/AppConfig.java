package com.spring.config;

import org.aspectj.lang.annotation.Aspect;
import com.spring.util.JdbcUtil;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(value = {"com.spring"})
@EnableTransactionManagement
@Import(value = {JdbcConfig.class})
public class AppConfig {


}
