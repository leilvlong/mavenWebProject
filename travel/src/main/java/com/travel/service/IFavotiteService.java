package com.travel.service;

import com.travel.domain.Favorite;
import com.travel.domain.User;

public interface IFavotiteService {
    public Favorite findUserFavorite(String rid, User user);

    public Integer addFavorite(String rid, User user);

}
