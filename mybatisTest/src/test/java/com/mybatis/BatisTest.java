package com.mybatis;

import com.mybatis.domain.Content;
import com.mybatis.domain.User;
import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BatisTest {
    public SqlSession sqlSession;
    public InputStream inputStream;
    public UserMapper mapper;

    @Before
    public void init() throws IOException {
        String resource = "SqlMapConfig.xml";
        inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    /**
     * 增 并返回添加的id
     * @throws IOException
     */
    @Test
    public void insetUser() throws IOException {
        User user1 = new User();
        user1.setUsername("jacdj");
        user1.setPassword("123789");

        int all = mapper.insertUser(user1);
        System.out.println(all);
        sqlSession.commit();
        System.out.println(user1);
    }

    /**
     * 删
     */
    @Test
    public void deleetUser() throws IOException {
        int all = mapper.deleteUser(18);
        System.out.println(all);
        sqlSession.commit();
    }

    /**
     * 改
     */
    @Test
    public void updateUser() throws IOException {

        User user1 = new User();
        user1.setId(15);
        user1.setUsername("jackq");
        user1.setPassword("123456");

        int all = mapper.updateUser(user1);
        System.out.println(all);
        sqlSession.commit();
    }


    /**
     * 查所有
     * @throws IOException
     */
    @Test
    public  void findAll() throws IOException {
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    /**
     * 模糊查询
     * @throws IOException
     */
    @Test
    public void likeAll() throws IOException {

        List<User> all = mapper.likeAll("j");
        sqlSession.commit();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void findNestUser(){
        User user = new User();
        user.setId(1);

        Content content = new Content();
        content.setUser(user);

        User nestUser = mapper.findNestUser(content);

        System.out.println(nestUser);
    }

    @After
    public void end() throws IOException {
        inputStream.close();
        sqlSession.close();
    }
}
