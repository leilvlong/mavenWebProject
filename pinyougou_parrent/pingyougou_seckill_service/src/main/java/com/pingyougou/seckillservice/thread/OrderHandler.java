package com.pingyougou.seckillservice.thread;

import com.pingyougou.pojos.SysConstants;
import com.pingyougou.utils.IdWorker;
import com.pingyougou.mapper.TbSeckillGoodsMapper;
import com.pingyougou.pojos.TbSeckillGoods;
import com.pingyougou.pojos.TbSeckillOrder;
import com.pingyougou.seckillservice.pojo.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述
 *
 * @author 三国的包子
 * @version 1.0
 * @package com.pinyougou.seckill.thread *
 * @since 1.0
 */


public class OrderHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    @Async//启用异步调用
    public void handlerOrder() {
        System.out.println("=======开始耗时操作=============" + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======结束耗时操作=============" + Thread.currentThread().getName());


        //1.从队列中获取元素
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps(SysConstants.SEC_KILL_USER_ORDER_LIST).rightPop();

        if (seckillStatus != null) {
            //2.获取用户ID 和 秒杀商品的ID
            TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(seckillStatus.getGoodsId());
            //3. 减库存
            tbSeckillGoods.setStockCount(tbSeckillGoods.getStockCount() - 1);
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillStatus.getGoodsId(), tbSeckillGoods);

            //4.如果库存为0 更新到数据库中
            if (tbSeckillGoods.getStockCount() == 0) {
                //日志
                seckillGoodsMapper.updateByPrimaryKeySelective(tbSeckillGoods);
                redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).delete(seckillStatus.getGoodsId());
            }

            //5.创建一个预订单到redis中
            TbSeckillOrder seckillOrder = new TbSeckillOrder();

            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setSeckillId(seckillStatus.getGoodsId());
            seckillOrder.setMoney(tbSeckillGoods.getCostPrice());
            seckillOrder.setSellerId(tbSeckillGoods.getSellerId());
            seckillOrder.setCreateTime(new Date());
            seckillOrder.setStatus("0");//未支付的状态

            redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).put(seckillStatus.getUserId(), seckillOrder);//hash   bigkey  field  value

            //订单创建成功 移除 排队的标记
            redisTemplate.boundHashOps(SysConstants.SEC_USER_QUEUE_FLAG_KEY).delete(seckillStatus.getUserId());

        }
    }


}
