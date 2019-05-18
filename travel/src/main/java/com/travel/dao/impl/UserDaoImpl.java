package com.travel.dao.impl;

import com.travel.dao.IUserDao;
import com.travel.domain.User;
import com.travel.utils.JdbcUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements IUserDao {
    JdbcTemplate jt = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        String sql = "select * from tab_user where username=?";
        try{
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        }catch (Exception e){
        }
        return user;
    }

    @Override
    public User userActive(String code) {
        User user = null;
        String sql = "select * from tab_user where code=?";
        try{
            user = jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        }catch (Exception e){
            user = new User();
        }
        return user;
    }

    @Override
    public boolean registerUser(User user) {
        String sql = "insert into tab_user values(0,?,?,?,?,?,?,?,?,?)";
        int update = 0;
        try{
            update = jt.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCode()
            );
        }catch (Exception e){
        }
        return 1 == update;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "update tab_user set username=?,password=?,name=?,telephone=?,status=? where uid=?";
        int update = 0;
        try{
            update = jt.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getTelephone(),
                    user.getStatus(),
                    user.getUid()
            );
        }catch (Exception e){
        }
        return 1 == update;
    }

}
