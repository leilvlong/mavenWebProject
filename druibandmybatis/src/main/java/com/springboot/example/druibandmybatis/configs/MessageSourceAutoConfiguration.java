package com.springboot.example.druibandmybatis.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @功能说明：国际化配置类
 * @创建人员：Chase Wang
 * @变更记录： 1、2018年09月26日 Chase Wang Create
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MessageSourceAutoConfiguration {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolverBean() {

        SessionLocaleResolver localeResolver = new SessionLocaleResolver();

        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        return new MessageResource();
    }

}

