package com.spring.config;

import org.aspectj.lang.annotation.Aspect;
import com.spring.util.JdbcUtil;
import com.spring.util.TransactionUtil;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(value = {"com.spring"})
@Import(value = {JdbcConfig.class, TransactionUtil.class})
public class AppConfig {


}
