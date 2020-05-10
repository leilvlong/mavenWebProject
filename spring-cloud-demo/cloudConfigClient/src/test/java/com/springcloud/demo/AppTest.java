package com.springcloud.demo;

import com.springcloud.demo.pojo.User;
import com.springcloud.demo.server.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigClentApplication.class)
public class AppTest {

    @Autowired
    private UserService userService;

    @Test
    public void test(){
        User user1 = new User();
        user1.setName("lynn");
        user1.setAge(10);

        User user2 = new User();
        user2.setName("zzz");
        user2.setAge(14);
        try {
            userService.addUser(user1,user2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
