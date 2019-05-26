package com.spring.dao.impl;

import com.spring.dao.UserDao;
import com.spring.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jt;

    public void setJt(JdbcTemplate jt) {
        this.jt = jt;

    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void delByUidUser(Integer uid) {

    }

    @Override
    public void updateByUid(Integer uid) {

    }

    @Override
    public User findUidByUser(Integer uid) {
        String sql = "select * from user where id=?";
        User user = null;
        try{
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), uid);
        }catch (Exception e){
        }

        return user;
    }

    @Override
    public List<User> finAllUser() {
        return null;
    }
}
