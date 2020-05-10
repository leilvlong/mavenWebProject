package com.springcloud.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface Test1Dao {

    @Insert("insert into datawarehouse1.test_user(name,age) values(#{name},#{age})")
    void addUser(@Param("name")String name, @Param("age") int age);

}
