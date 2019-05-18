package com.travel.dao;

import com.travel.domain.Favorite;
import com.travel.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public interface IFavoriteDao {
    public Favorite findUserFavorite(String rid, User user);

    public Integer findRouteCount(String rid);

    public void updateRouteCount(JdbcTemplate lockJt,String rid);

    public void addFavorite(JdbcTemplate lockJt,String rid, User user);



}
