package com.pingyougou.seckillcontroller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.pingyougou.payinterface.WeixinPayService;
import com.pingyougou.pojos.TbSeckillOrder;
import com.pingyougou.seckillservice.SeckillOrderService;
import com.pingyougou.utils.HttpClient;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.cart.controller *
 * @since 1.0
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService payService;


    @Reference
    private SeckillOrderService seckillOrderService;


    @RequestMapping("/createNative")
    public Map<String, String> createNative() {

        //1.获取用户的ID
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        //2.从redis中获取预订单 获取预订单的金额  和 支付订单号
        TbSeckillOrder seckillOrder = seckillOrderService.findOrderByUserId(userId);

        if(seckillOrder!=null){
            double v = seckillOrder.getMoney().doubleValue()*100;
            long fen = (long)v;
            return  payService.createNative(seckillOrder.getId()+"", fen+"");
        }else{
            return new HashMap<>();
        }
    }

    @RequestMapping("/queryStatus")
    public Result queryStatus(String out_trade_no) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        //window.setInterval();

        Result result = new Result(false, "支付失败");

        int count = 0;
        while (true) {
            Map<String, String> resultMap = payService.queryStatus(out_trade_no);

            count++;

            if (count >= 100) {
                result = new Result(false, "支付超时");



                // 关闭微信支付订单
                Map<String,String> resultmap = payService.closePay(out_trade_no);

                if("SUCCESS".equals(resultmap.get("result_code"))){
                    //关闭微信的订单成功

                    //删除订单
                    //1. 恢复redis中的商品的库存
                    //2. 删除预订单
                    //4. 恢复队列
                    seckillOrderService.deleteOrder(userId);
                }else if("ORDERPAID".equals(resultmap.get("err_code"))){
                    seckillOrderService.updateOrderStatus(userId,resultMap.get("transaction_id"));
                }else {
                   // 不用管了
                }



                break;
            }

            if (resultMap == null) {
                result = new Result(false, "支付失败");
                break;
            }
            if ("SUCCESS".equals(resultMap.get("trade_state"))) {//支付成功
                result = new Result(true, "支付成功");
                /**
                 *
                 + 更新redis中的预订单的状态 ( status 支付时间,更新微信的交易流水)
                 + 更新到数据库中.
                 + 删除redis中的域订单.
                 */
                seckillOrderService.updateOrderStatus(userId,resultMap.get("transaction_id"));
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
