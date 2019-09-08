package com.pingyougou.carinterface;

import entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);

    void saveToRedis(String name, List<Cart> newestList);

    List<Cart> getCartListFromRedis(String name);

    List<Cart> mergeCartList(List<Cart> cookieCartList, List<Cart> redisList);
}
