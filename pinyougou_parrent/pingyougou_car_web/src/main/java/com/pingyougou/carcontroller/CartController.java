package com.pingyougou.carcontroller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pingyougou.carinterface.CartService;
import com.pingyougou.utils.CookieUtil;
import entity.Cart;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference
    private CartService cartService;

    /**
     * @param itemId 要添加的商品SKU的ID
     * @param num    购买的数量
     * @return
     */
    @CrossOrigin(origins = "http://localhost:9105", allowCredentials = "true")
    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {

        try{
            //1.获取用户名 anonymousUser springsecurity 内置的角色的用户名 表示匿名用户
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            //2.判断用户是否登录 如果没登录 操作cookie
            if ("anonymousUser".equals(username)) {
                //2.1 从cookie中获取已有的购物车列表数据 List<Cart>
                String cartValue = CookieUtil.getCookieValue(request, "cartList",true);
                if (StringUtils.isEmpty(cartValue)){
                    cartValue = "[]";
                }
                List<Cart> cartList = JSON.parseArray(cartValue, Cart.class);
                //2.2 向已有的购物车列表中 添加 商品   返回一个最新的购物车列表
                // 写一个方法 (向已有的购物车中添加商品:)
                cartList = cartService.addGoodsToCartList(cartList, itemId, num);

                //2.3 将最新的购物车数据设置回cookie中.
                String cartListStr = JSON.toJSONString(cartList);

                CookieUtil.setCookie(request,response,"cartList",cartListStr,7*24*60*60,true);
            } else {
                //3.如果登录 操作的redis
                //3.1 从redis中获取已有的购物车列表数据
                List<Cart> cartListFromRedis = cartService.getCartListFromRedis(username);
                //3.2 向已有购物车列表中添加 商品 返回一个最新的购物车的列表
                cartListFromRedis = cartService.addGoodsToCartList(cartListFromRedis, itemId, num);
                //3.3 将最新的购物车数据 存储回redis中
                cartService.saveToRedis(username,cartListFromRedis);
            }
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }

    }

    @RequestMapping("/findCartList")
    public List<Cart> findCartList(HttpServletRequest request, HttpServletResponse response){
        //1.获取用户名 anonymousUser springsecurity 内置的角色的用户名 表示匿名用户
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //2. 判断用户登录状况
        if ("anonymousUser".equals(username)) {
            //展示cookie中的购物车的数据
            String cartValue = CookieUtil.getCookieValue(request, "cartList",true);
            if (StringUtils.isEmpty(cartValue)){
                cartValue = "[]";
            }
            List<Cart> cartList = JSON.parseArray(cartValue, Cart.class);
            return cartList;
        }else{
            //如果用户已经登录则合并购物车的数据  redis与cookie中的
            //1.获取cookie中的购物车的数据
            String cookieCartValue = CookieUtil.getCookieValue(request, "cartList",true);
            if (StringUtils.isEmpty(cookieCartValue)){
                cookieCartValue = "[]";
            }
            List<Cart> cookieCartList = JSON.parseArray(cookieCartValue, Cart.class);

            //2.获取redis中的购物车的数据
            List<Cart> cartListFromRedis = cartService.getCartListFromRedis(username);
            if (cartListFromRedis == null){
                cartListFromRedis = new ArrayList<>();
            }

            //3.合并(业务) 之后 返回一个最新的购物车的数据
            List<Cart> newEsCartList = cartService.mergeCartList(cookieCartList, cartListFromRedis);

            //4.将最新的数据重新的设置回redis中
            cartService.saveToRedis(username,newEsCartList);

            //5.cookie中的购物车清除
            CookieUtil.deleteCookie(request,response,"cartList" );

            return newEsCartList;
        }
    }
}
