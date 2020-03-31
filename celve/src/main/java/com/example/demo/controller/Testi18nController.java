package com.example.demo.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


/**
 * thymeleaf国际化
 */

@Controller
@RequestMapping("/")
public class Testi18nController {

    @RequestMapping("/")
    public String index(){
        return "views/index";
    }

    @RequestMapping("/changeLang")
    @ResponseBody
    public String changeLang(String lang, HttpServletRequest request){
        if ("zh_cn".equals(lang)){
            Locale locale = new Locale("zh","CN");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            request.getSession().setAttribute("lan","zh_CN");
        }else if("en_us".equals(lang)){
            Locale locale = new Locale("en","US");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            request.getSession().setAttribute("lan","en_US");
        }else if("hw_hw".equals(lang)){
            Locale locale = new Locale("hw","HW");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
            request.getSession().setAttribute("lan","hw_HW");
        } else{
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
            request.getSession().setAttribute("lan","zh_CN");
        }
        return "ok";
    }
}
