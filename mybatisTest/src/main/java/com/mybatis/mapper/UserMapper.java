package com.mybatis.mapper;

import com.mybatis.domain.Content;
import com.mybatis.domain.User;

import java.util.List;

public interface UserMapper {
    public int insertUser(User user);

    public int deleteUser(Integer id);

    public int updateUser(User user);

    public List<User> findAll();

    public List<User> likeAll(String username);

    public User findNestUser(Content content);
}
