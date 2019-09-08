package com.pingyougou.carinterface.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pingyougou.carinterface.CartService;
import com.pingyougou.mapper.TbItemMapper;
import com.pingyougou.pojos.TbItem;
import com.pingyougou.pojos.TbOrderItem;
import entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CartService {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1.根据商品的ID 获取商品的对象数据
        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

        //2.获取商品对象数据中所属的 商家的ID
        String sellerId = tbItem.getSellerId();

        //验证已有的购物中是否有商家id ,若商家id不存在则不会存在商品已存在
        Cart cart = searchCartBySellerId(cartList, sellerId);
        if(cart == null) {
            //3.判断 已有的购物中 是否 有商家ID  如果没有  直接添加商品
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(tbItem.getSeller());
            ArrayList<TbOrderItem> orderItemList = new ArrayList<>();

            //添加需要返回的信息
            TbOrderItem tbOrderItem = new TbOrderItem();
            tbOrderItem.setItemId(tbItem.getId());
            tbOrderItem.setGoodsId(tbItem.getGoodsId());
            tbOrderItem.setTitle(tbItem.getTitle());
            tbOrderItem.setPrice(tbItem.getPrice());
            tbOrderItem.setNum(num);
            tbOrderItem.setTotalFee(new BigDecimal((num*(tbItem.getPrice().doubleValue()))));
            tbOrderItem.setPicPath(tbItem.getImage());
            tbOrderItem.setSellerId(tbItem.getSellerId());

            // 将该商家购买的购物信息添加到整个购物车容器中
            orderItemList.add(tbOrderItem);
            cart.setOrderItemList(orderItemList);
            cartList.add(cart);
        }else {
            //此处商家id以存在,则判断用户是否在该商家购买同样的商品
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            TbOrderItem tbOrderItem = searchItemByItemId(orderItemList, tbItem.getId());

            //判断 商家的购物明细中 是否有 要添加的商品 如果 有  数量相加
            if (tbOrderItem != null){
                tbOrderItem.setNum(num+tbOrderItem.getNum());
                tbOrderItem.setTotalFee(new BigDecimal((tbOrderItem.getNum()*(tbItem.getPrice().doubleValue()))));

                //当购买商品数量为 0 时  删除该记录
                if (tbOrderItem.getNum()<=0){
                    orderItemList.remove(tbOrderItem);
                }
                // 当该商家无购买商品记录是  删除该相关商家的购物记录
                if (orderItemList.size()<=0){
                    cartList.remove(orderItemList);
                }

            }else{
                //若无该商品记录  则直接添加
                tbOrderItem = new TbOrderItem();
                tbOrderItem.setItemId(tbItem.getId());
                tbOrderItem.setGoodsId(tbItem.getGoodsId());
                tbOrderItem.setTitle(tbItem.getTitle());
                tbOrderItem.setPrice(tbItem.getPrice());
                tbOrderItem.setNum(num);
                tbOrderItem.setTotalFee(new BigDecimal((num*(tbItem.getPrice().doubleValue()))));
                tbOrderItem.setPicPath(tbItem.getImage());
                tbOrderItem.setSellerId(tbItem.getSellerId());

                orderItemList.add(tbOrderItem);
            }
        }
        return cartList;

    }



    @Override
    @SuppressWarnings("unchecked")
    public void saveToRedis(String name, List<Cart> newestList) {
        redisTemplate.boundHashOps("CART_REDIS_KEY").put(name,newestList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cart> getCartListFromRedis(String name) {
        return (List<Cart>) redisTemplate.boundHashOps("CART_REDIS_KEY").get(name);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cookieCartList, List<Cart> cartListFromRedis) {
        for (Cart cart : cookieCartList) {
            List<TbOrderItem> orderItemList = cart.getOrderItemList();
            for (TbOrderItem tbOrderItem : orderItemList) {
                cartListFromRedis = addGoodsToCartList(cartListFromRedis, tbOrderItem.getItemId(), tbOrderItem.getNum());
            }
        }
        return cartListFromRedis;
    }

    private TbOrderItem searchItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {
        for (TbOrderItem tbOrderItem : orderItemList) {
            if (tbOrderItem.getItemId().equals(itemId)){
                return tbOrderItem;
            }
        }
        return null;
    }

    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }
        return null;
    }
}


