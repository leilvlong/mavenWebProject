package com.pingyougou.carcontroller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.pingyougou.orderinterface.OrderService;
import com.pingyougou.pojos.TbOrder;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody TbOrder order) {
        try {

            if(orderService == null){
                System.out.println("kong");
                return new Result(false, "失败");
            }else{
                String userName = SecurityContextHolder.getContext().getAuthentication().getName();
                order.setUserId(userName);
                orderService.add(order);
                return new Result(true, "成功");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }
}
