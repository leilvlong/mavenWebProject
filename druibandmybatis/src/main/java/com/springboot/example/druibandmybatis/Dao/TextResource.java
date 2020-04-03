package com.springboot.example.druibandmybatis.Dao;

import com.springboot.example.druibandmybatis.pojo.I18n;
import com.springboot.example.druibandmybatis.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TextResource {

    @Select("SELECT * FROM `i18n`")
    List<I18n> findAll();

    @Insert("insert into user values(0,#{username},#{password},#{age},#{email},#{sex})")
    void insetrUser(User user);

    User findAllUser(Long age);
}
