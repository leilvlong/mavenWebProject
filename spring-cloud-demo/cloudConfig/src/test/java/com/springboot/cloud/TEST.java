package com.springboot.cloud;

import com.springcloud.demo.ConfigApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigApplication.class)
public class TEST {

    @Qualifier("jasyptStringEncryptor")
    @Autowired
    StringEncryptor encrypt;

    @Test
    public void test(){
        //x8+lLvWdSBZnD35dP3uY1oyxr9Rp8NU/
        String password = "Lei749581";
        String encryptPwd = encrypt.encrypt(password);
        System.out.println("加密:：" + encryptPwd);
        System.out.println("解密：" + encrypt.decrypt(encryptPwd));
    }
}
