package com.travel.dao.impl;

import com.travel.dao.IFavoriteDao;
import com.travel.domain.Favorite;
import com.travel.domain.User;
import com.travel.utils.DateUtil;
import com.travel.utils.JdbcUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class FavoriteDaoImpl implements IFavoriteDao {
    private JdbcTemplate jt = new JdbcTemplate(JdbcUtil.getDataSource());


    @Override
    public Favorite findUserFavorite(String rid, User user) {
        String sql = "select * from tab_favorite where rid=? and uid=?";
        Favorite favorite = null;
        try{
            favorite = jt.queryForObject(sql,new BeanPropertyRowMapper<>(Favorite.class),rid,user.getUid());
        }catch (Exception e){
        }

        return favorite;
    }

    @Override
    public Integer findRouteCount(String rid) {
        String sql = "select count from tab_route where rid=?";
        Integer count = null;
        try{
            count = jt.queryForObject(sql, Integer.class, rid);
        }catch (Exception e){
        }
        return count;
    }

    @Override
    public void updateRouteCount(JdbcTemplate lockJt, String rid) {
        String sql = "update tab_route set count=count+1 where rid=?";
        try {
            lockJt.update(sql,rid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addFavorite(JdbcTemplate lockJt,String rid, User user) {
        String sql = "insert into tab_favorite values(?,?,?)";
        try{
            jt.update(sql,rid, DateUtil.getCurrentDate(),user.getUid());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUid(5);


    }

}
