package com.example.demo.aop;

import com.example.demo.annotations.Lock;
import com.example.demo.annotations.OpLog;
import com.example.demo.components.RedisLock;
import com.example.demo.responses.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@Order
public class LockAop {

    @Autowired
    RedisLock redisLock;

    @Autowired
    HttpServletResponse httpServletResponse;

    @Pointcut("@annotation(com.example.demo.annotations.Lock)")
    public void opLogAopApi(){
    }

    @Around(value = "opLogAopApi() && @annotation(lock)")
    public Object doAround(ProceedingJoinPoint pjp, Lock lock) throws Throwable {
        String key = lock.key();
        long l = lock.timeOut();

        boolean b = redisLock.tryLock(key, l);

        if (b){
            Thread.sleep(2000);
            String requestId = redisLock.getRequestId(key);
            redisLock.releaseLock(key, requestId);
            return pjp.proceed();
        }



        Response response = new Response();
        response.setStatus(500);
        response.setMessage("失败");

        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(response);


        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        try{
            outputStream.write(value.getBytes());
        }finally {
            outputStream.flush();
            outputStream.close();
        }

        return null;
    }

}
