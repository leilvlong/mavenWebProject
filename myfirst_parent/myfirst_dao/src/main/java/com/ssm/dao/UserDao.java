package com.ssm.dao;

import com.ssm.daomain.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface UserDao {

    @Select("select * from user where id=#{id}")
    public User findIdByUser(Integer id);

    @Update("update user set password=#{password} where id=#{id}")
    public void updateByUser(User user);
}
