package com.mybatis.mapper;

import com.mybatis.domain.User;

import java.util.List;

public interface UserMapper {
    public List<User> findAll();

    public int insertUser(User user);

    public List<User> likeAll(String username);
}
