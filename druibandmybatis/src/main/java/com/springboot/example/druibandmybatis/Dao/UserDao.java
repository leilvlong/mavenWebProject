package com.springboot.example.druibandmybatis.Dao;

import com.springboot.example.druibandmybatis.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user values(0,#{username},#{password},#{age},#{email},#{sex})")
    void insetrUser(User user);

}
