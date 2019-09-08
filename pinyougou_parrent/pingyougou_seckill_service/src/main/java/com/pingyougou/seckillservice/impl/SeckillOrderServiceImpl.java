package com.pingyougou.seckillservice.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.pingyougou.pojos.SysConstants;
import com.pingyougou.utils.IdWorker;
import com.pingyougou.mapper.TbSeckillGoodsMapper;
import com.pingyougou.pojos.TbSeckillGoods;
import com.pingyougou.seckillservice.pojo.SeckillStatus;
import com.pingyougou.seckillservice.SeckillOrderService;
import com.pingyougou.seckillservice.thread.OrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import com.pingyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pingyougou.mapper.TbSeckillOrderMapper;
import com.pingyougou.pojos.TbSeckillOrder;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SeckillOrderServiceImpl extends CoreServiceImpl<TbSeckillOrder> implements SeckillOrderService {


    private TbSeckillOrderMapper seckillOrderMapper;

    @Autowired
    public SeckillOrderServiceImpl(TbSeckillOrderMapper seckillOrderMapper) {
        super(seckillOrderMapper, TbSeckillOrder.class);
        this.seckillOrderMapper = seckillOrderMapper;
    }


    @Override
    public PageInfo<TbSeckillOrder> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<TbSeckillOrder> all = seckillOrderMapper.selectAll();
        PageInfo<TbSeckillOrder> info = new PageInfo<TbSeckillOrder>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbSeckillOrder> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }


    @Override
    public PageInfo<TbSeckillOrder> findPage(Integer pageNo, Integer pageSize, TbSeckillOrder seckillOrder) {
        PageHelper.startPage(pageNo, pageSize);

        Example example = new Example(TbSeckillOrder.class);
        Example.Criteria criteria = example.createCriteria();

        if (seckillOrder != null) {
            if (StringUtils.isNotBlank(seckillOrder.getUserId())) {
                criteria.andLike("userId", "%" + seckillOrder.getUserId() + "%");
                //criteria.andUserIdLike("%"+seckillOrder.getUserId()+"%");
            }
            if (StringUtils.isNotBlank(seckillOrder.getSellerId())) {
                criteria.andLike("sellerId", "%" + seckillOrder.getSellerId() + "%");
                //criteria.andSellerIdLike("%"+seckillOrder.getSellerId()+"%");
            }
            if (StringUtils.isNotBlank(seckillOrder.getStatus())) {
                criteria.andLike("status", "%" + seckillOrder.getStatus() + "%");
                //criteria.andStatusLike("%"+seckillOrder.getStatus()+"%");
            }
            if (StringUtils.isNotBlank(seckillOrder.getReceiverAddress())) {
                criteria.andLike("receiverAddress", "%" + seckillOrder.getReceiverAddress() + "%");
                //criteria.andReceiverAddressLike("%"+seckillOrder.getReceiverAddress()+"%");
            }
            if (StringUtils.isNotBlank(seckillOrder.getReceiverMobile())) {
                criteria.andLike("receiverMobile", "%" + seckillOrder.getReceiverMobile() + "%");
                //criteria.andReceiverMobileLike("%"+seckillOrder.getReceiverMobile()+"%");
            }
            if (StringUtils.isNotBlank(seckillOrder.getReceiver())) {
                criteria.andLike("receiver", "%" + seckillOrder.getReceiver() + "%");
                //criteria.andReceiverLike("%"+seckillOrder.getReceiver()+"%");
            }
            if (StringUtils.isNotBlank(seckillOrder.getTransactionId())) {
                criteria.andLike("transactionId", "%" + seckillOrder.getTransactionId() + "%");
                //criteria.andTransactionIdLike("%"+seckillOrder.getTransactionId()+"%");
            }

        }
        List<TbSeckillOrder> all = seckillOrderMapper.selectByExample(example);
        PageInfo<TbSeckillOrder> info = new PageInfo<TbSeckillOrder>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbSeckillOrder> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderHandler orderHandler;

    @Override
    public void submitOrder(Long id, String userId) {
        //1.根据ID 从redis中获取秒杀商品
        TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(id);


        //先判断 用户是否已经在排队中  如果 在,提示已在排队中

        Object o2 = redisTemplate.boundHashOps(SysConstants.SEC_USER_QUEUE_FLAG_KEY).get(userId);

        if (o2 != null) {
            throw new RuntimeException("排队中");
        }


        //先判断 是否有未支付的订单
        Object o1 = redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId);

        if (o1 != null) {
            throw new RuntimeException("有未支付的订单");
        }


        //2.判断商品是否已经售罄 如果卖完了 抛出异常
        /*if (tbSeckillGoods == null || tbSeckillGoods.getStockCount() <= 0) {
            throw new RuntimeException("卖完了");
        }*/
        Object o = redisTemplate.boundListOps(SysConstants.SEC_KILL_GOODS_PREFIX + id).rightPop();
        if (o == null) {
            throw new RuntimeException("卖完了");
        }


        //压入队列
        redisTemplate.boundListOps(SysConstants.SEC_KILL_USER_ORDER_LIST).leftPush(new SeckillStatus(userId, id, SeckillStatus.SECKILL_queuing));

        //此时 应该 表示用户只能进入队列中,不能保证一定创建订单成功.


        //标记 用户已经进入排队中
        redisTemplate.boundHashOps(SysConstants.SEC_USER_QUEUE_FLAG_KEY).put(userId, id);

        //调用多线程
        orderHandler.handlerOrder();


    }

    @Override
    public TbSeckillOrder findOrderByUserId(String userId) {
        return (TbSeckillOrder) redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId);
    }


    @Override
    public void updateOrderStatus(String userId, String transaction_id) {
        //1.根据用户的ID 获取订单
        TbSeckillOrder tbSeckillOrder = findOrderByUserId(userId);
        if (tbSeckillOrder != null) {
            //2.更新订单的数据
            tbSeckillOrder.setStatus("1");//已经支付
            tbSeckillOrder.setPayTime(new Date());//支付时间
            tbSeckillOrder.setTransactionId(transaction_id);//交易流水
            //3.更新到数据库中
            seckillOrderMapper.insert(tbSeckillOrder);
            //4.删除预订单
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).delete(userId);
        }

    }




    @Override
    public void deleteOrder(String userId) {
        TbSeckillOrder tbSeckillOrder = (TbSeckillOrder) redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).get(userId);
        //1. 恢复redis中的商品的库存
        Long seckillId = tbSeckillOrder.getSeckillId();

        TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(seckillId);

        if(tbSeckillGoods==null){
            //从数据库中获取秒杀商品 此时的秒杀商品的剩余库是0
            TbSeckillGoods tbSeckillGoods1 = seckillGoodsMapper.selectByPrimaryKey(seckillId);
            tbSeckillGoods1.setStockCount(1);
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillId, tbSeckillGoods1);
            //更新回书库中
            seckillGoodsMapper.updateByPrimaryKeySelective(tbSeckillGoods1);
        }else {
            tbSeckillGoods.setStockCount(tbSeckillGoods.getStockCount() + 1);
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(seckillId, tbSeckillGoods);
        }
        //2. 删除预订单
        redisTemplate.boundHashOps(SysConstants.SEC_KILL_ORDER).delete(userId);
        //4. 恢复队列
        redisTemplate.boundListOps(SysConstants.SEC_KILL_GOODS_PREFIX + seckillId).leftPush(seckillId);



    }


}
