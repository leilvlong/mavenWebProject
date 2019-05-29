package com.spring.config;


import com.spring.util.JdbcUtil;
import com.spring.util.TransactionUtil;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(value = {"com.spring"})
@Import(value = {JdbcConfig.class, TransactionUtil.class,ServletServiceConfig.class})
public class AppConfig {


}
