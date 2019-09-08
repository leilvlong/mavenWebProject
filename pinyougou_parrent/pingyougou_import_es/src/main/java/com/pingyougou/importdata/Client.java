package com.pingyougou.importdata;

import com.pingyougou.importdata.service.ImportServic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-es.xml");
        ImportServic importService = context.getBean(ImportServic.class);
        importService.importData();
    }
}
