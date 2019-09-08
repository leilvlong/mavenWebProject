package com.pingyougou.seckill;

import com.pingyougou.mapper.TbSeckillGoodsMapper;
import com.pingyougou.pojos.SysConstants;

import com.pingyougou.pojos.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class SecKillGoods {


    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 一个被反复执行的方法,
     * 通过注解 来指定 cron表达式:指定何时执行的
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void pushGoods() {

        //1.注入dao

        //2.执行查询语句  符合条件的查询语句
        //select * from tb_seckill_good where status=1 and stock_count>0 and   开始<当前时间<结束时间 and id not in (12,3,3,4)
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");//审核过的
        criteria.andGreaterThan("stockCount",0);//剩余库存>0
        Date date = new Date();
        criteria.andLessThan("startTime",date);
        criteria.andGreaterThan("endTime",date);


        //排除redis中已有的商品的列表
        Set<Long> seckillGoods = redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).keys();
        if(seckillGoods!=null && seckillGoods.size()>0) {
            criteria.andNotIn("id", seckillGoods);
        }

        List<TbSeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);

        //3.注入redisTemplate

        //4.存储到redis中  key  value   hash   bigkey  field  value

        for (TbSeckillGoods tbSeckillGoods : seckillGoodsList) {
            pushGoodsList(tbSeckillGoods);
            redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).put(tbSeckillGoods.getId(),tbSeckillGoods);
        }

    }

    /**
     * 一个队列 就是一种商品
     * 队列的长度 就是 商品的库存两
     * @param tbSeckillGoods
     */
    private void pushGoodsList(TbSeckillGoods tbSeckillGoods ){

        for (Integer i = 0; i < tbSeckillGoods.getStockCount(); i++) {

            redisTemplate.boundListOps(SysConstants.SEC_KILL_GOODS_PREFIX+tbSeckillGoods.getId()).leftPush(tbSeckillGoods.getId());
        }

    }
}
