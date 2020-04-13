package com.springboot.example.druibandmybatis.Dao;

import com.springboot.example.druibandmybatis.pojo.I18n;
import com.springboot.example.druibandmybatis.pojo.ListUser;
import com.springboot.example.druibandmybatis.pojo.NewUser;
import com.springboot.example.druibandmybatis.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TextResource extends tk.mybatis.mapper.common.Mapper<NewUser> {

    @Select("SELECT * FROM `i18n`")
    List<I18n> findAll();

    @Insert("insert into user values(0,#{username},#{password},#{age},#{email},#{sex})")
    void insetrUser(User user);

    User findAllUser(Long age);

    List<ListUser> findListUser(Long age);

}
