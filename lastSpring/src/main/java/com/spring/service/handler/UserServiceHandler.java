/*
package com.spring.service.handler;

import com.spring.service.UserService;
import com.spring.service.impl.UserServiceImpl;
import com.spring.util.TransactionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class UserServiceHandler implements InvocationHandler {

    @Resource(name="userServiceImpl")
    private UserServiceImpl us;

    @Resource(name="TransactionUtil")
    private TransactionUtil tu;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            tu.startAffair();
            method.invoke(us,args);
            tu.commit();

        }catch (Exception e){
            System.out.println("出异常了");
            tu.rollback();
        }
        return null;
    }
}
*/
