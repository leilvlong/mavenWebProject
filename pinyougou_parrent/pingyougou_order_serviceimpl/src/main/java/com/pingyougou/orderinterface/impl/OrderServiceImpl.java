package com.pingyougou.orderinterface.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pingyougou.mapper.TbOrderItemMapper;
import com.pingyougou.mapper.TbOrderMapper;
import com.pingyougou.mapper.TbPayLogMapper;
import com.pingyougou.orderinterface.OrderService;
import com.pingyougou.pojos.TbOrder;
import com.pingyougou.pojos.TbOrderItem;
import com.pingyougou.pojos.TbPayLog;
import com.pingyougou.utils.IdWorker;
import entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl  implements OrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderMapper orderMapper;




    @Autowired
    private IdWorker idWorker;

    @Autowired
    private TbPayLogMapper payLogMapper;


    @Override
    public void add(TbOrder order) {
        //1.获取页面传递的数据
        //2.插入到订单表中         拆单(一个商家就是一个订单)  订单的ID 要生成

        //2.1 获取reids中的购物车数据
        //  "CART_REDIS_KEY"
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("CART_REDIS_KEY").get(order.getUserId());
        //2.2 循环遍历 每一个Cart 对象  就是一个商家
        List<String> orderListiD = new ArrayList<>();
        double totalMoney = 0;
        for (Cart cart : cartList) {
            //3.插入到订单选项表中
            TbOrder tbOrder = new TbOrder();

            long orderId = idWorker.nextId();
            orderListiD.add(orderId + "");
            tbOrder.setOrderId(orderId);
            tbOrder.setPaymentType(order.getPaymentType());
            tbOrder.setPostFee("0");
            tbOrder.setStatus("1");//未付款的状态
            tbOrder.setCreateTime(new Date());
            tbOrder.setUpdateTime(tbOrder.getCreateTime());
            tbOrder.setUserId(order.getUserId());
            tbOrder.setReceiverAreaName(order.getReceiverAreaName());
            tbOrder.setReceiver(order.getReceiver());
            tbOrder.setReceiverZipCode(order.getReceiverZipCode());
            tbOrder.setReceiver(order.getReceiver());
            tbOrder.setSourceType("2");
            tbOrder.setSellerId(cart.getSellerId());

            double money = 0;
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                // 拆单 根据购物车的结算信息为每个商户设置对应的订单 其订单与用户结算购物车订单关联
                money += orderItem.getTotalFee().doubleValue();
                orderItem.setId(idWorker.nextId());
                orderItem.setOrderId(orderId);
                orderItemMapper.insert(orderItem);
            }
            totalMoney += money;
            tbOrder.setPayment(new BigDecimal(money));
            orderMapper.insert(tbOrder);
        }
        TbPayLog payLog = new TbPayLog();

        payLog.setOutTradeNo(idWorker.nextId() + "");
        payLog.setCreateTime(new Date());
        long fen = (long) (totalMoney * 100);//fen
        payLog.setTotalFee(fen);
        payLog.setUserId(order.getUserId());
        payLog.setTradeState("0");//未支付
        // 38,37
        payLog.setOrderList(orderListiD.toString().replace("[", "").replace("]", ""));//[1,2]
        payLog.setPayType(order.getPaymentType());//微信支付
        payLogMapper.insert(payLog);

        //存储到redis中  bigkey field  value
        redisTemplate.boundHashOps("TbPayLog").put(order.getUserId(), payLog);

        //移除掉redis的购物车数据
        redisTemplate.boundHashOps("CART_REDIS_KEY").delete(order.getUserId());
    }

    @Override
    public TbPayLog getPayLogByUserId(String userId) {
        return (TbPayLog) redisTemplate.boundHashOps("TbPayLog").get(userId);
    }

    @Override
    public void updateStatus(String out_trade_no, String transaction_id) {
        //1.根据订单号 查询支付日志对象  更新他的状态
        TbPayLog payLog = payLogMapper.selectByPrimaryKey(out_trade_no);

        payLog.setPayTime(new Date());
        payLog.setTransactionId(transaction_id);
        payLog.setTradeState("1");//
        payLogMapper.updateByPrimaryKey(payLog);


        //* 2.根据支付日志 获取到商品订单列表 更新商品订单状态
        String orderList = payLog.getOrderList();//   37,38
        String[] split = orderList.split(",");
        for (String orderidstring : split) {// 37 38
            TbOrder tbOrder = orderMapper.selectByPrimaryKey(Long.valueOf(orderidstring));
            tbOrder.setStatus("2");//已经付完款
            tbOrder.setUpdateTime(new Date());
            tbOrder.setPaymentTime(tbOrder.getUpdateTime());
            orderMapper.updateByPrimaryKey(tbOrder);
        }

        //* 3.根据支付日志 获取到USER_id 删除reids中的记录
        redisTemplate.boundHashOps("TbPayLog").delete(payLog.getUserId());
    }
}
