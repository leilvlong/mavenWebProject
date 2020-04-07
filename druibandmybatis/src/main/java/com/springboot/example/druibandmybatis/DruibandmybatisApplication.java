package com.springboot.example.druibandmybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DruibandmybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruibandmybatisApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }
}
