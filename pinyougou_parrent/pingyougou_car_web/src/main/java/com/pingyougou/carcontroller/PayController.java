package com.pingyougou.carcontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pingyougou.orderinterface.OrderService;
import com.pingyougou.payinterface.WeixinPayService;
import com.pingyougou.pojos.TbPayLog;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService payService;

    @Reference
    private OrderService orderService;


    @RequestMapping("/createNative")
    public Map<String, String> createNative() {
        System.out.println("111");
        //1.获取用户的ID
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        //2.调用orderservice的服务 从redis获取支付日志对象
        TbPayLog payLog =  orderService.getPayLogByUserId(userId);

        //3.获取金额 和 订单号

      /*  //1.生成一个[支付]订单
        String out_trade_no = new IdWorker().nextId() + "";
        //2.获取商品订单的总金额(先写死)
        String total_fee = "1";//单位是分*/
        //3.调用服务(内部实现调用统一下单API)
        System.out.println("222");
        return payService.createNative(payLog.getOutTradeNo(), payLog.getTotalFee()+"");
    }

    @RequestMapping("/queryStatus")
    public Result queryStatus(String out_trade_no) {
        //直接轮询调用 pay-service的接口方法 查询该out_trade_no对应的支付状态 返回数据
        Result result = new Result(false, "支付失败");

        //有一个超时时间 如果进过了5分钟还没支付就是表示超时
        int count = 0;
        while (true) {
            Map<String, String> resultMap = payService.queryStatus(out_trade_no);

            count++;

            if (count >= 16) {
                result = new Result(false, "支付超时");
                break;
            }

            if (resultMap == null) {
                result = new Result(false, "支付失败");
                break;
            }
            if ("SUCCESS".equals(resultMap.get("trade_state"))) {//支付成功
                result = new Result(true, "支付成功");

                       /* + 修改商品的订单的状态
                        + 支付日志的订单的状态
                        + 删除掉redis中的支付日志*/
                orderService.updateStatus(out_trade_no,resultMap.get("transaction_id"));


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
