package com.example.demo.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Configuration
@EnableAsync
@EnableScheduling
public class TestTask {

    @Scheduled(fixedDelay = 3000)
    @Async
    public void tetsTask(){
        System.out.println("正在执行定时任务"+ new Date().toString());
    }
}
