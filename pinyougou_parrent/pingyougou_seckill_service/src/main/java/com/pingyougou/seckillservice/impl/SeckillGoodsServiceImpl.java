package com.pingyougou.seckillservice.impl;

import java.util.*;

import com.pingyougou.pojos.SysConstants;
import com.pingyougou.seckillservice.SeckillGoodsService;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import com.pingyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pingyougou.mapper.TbSeckillGoodsMapper;
import com.pingyougou.pojos.TbSeckillGoods;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SeckillGoodsServiceImpl extends CoreServiceImpl<TbSeckillGoods> implements SeckillGoodsService {


    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    public SeckillGoodsServiceImpl(TbSeckillGoodsMapper seckillGoodsMapper) {
        super(seckillGoodsMapper, TbSeckillGoods.class);
        this.seckillGoodsMapper = seckillGoodsMapper;
    }


    @Override
    public PageInfo<TbSeckillGoods> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<TbSeckillGoods> all = seckillGoodsMapper.selectAll();
        PageInfo<TbSeckillGoods> info = new PageInfo<TbSeckillGoods>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbSeckillGoods> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }


    @Override
    public PageInfo<TbSeckillGoods> findPage(Integer pageNo, Integer pageSize, TbSeckillGoods seckillGoods) {
        PageHelper.startPage(pageNo, pageSize);

        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();

        if (seckillGoods != null) {
            if (StringUtils.isNotBlank(seckillGoods.getTitle())) {
                criteria.andLike("title", "%" + seckillGoods.getTitle() + "%");
                //criteria.andTitleLike("%"+seckillGoods.getTitle()+"%");
            }
            if (StringUtils.isNotBlank(seckillGoods.getSmallPic())) {
                criteria.andLike("smallPic", "%" + seckillGoods.getSmallPic() + "%");
                //criteria.andSmallPicLike("%"+seckillGoods.getSmallPic()+"%");
            }
            if (StringUtils.isNotBlank(seckillGoods.getSellerId())) {
                criteria.andLike("sellerId", "%" + seckillGoods.getSellerId() + "%");
                //criteria.andSellerIdLike("%"+seckillGoods.getSellerId()+"%");
            }
            if (StringUtils.isNotBlank(seckillGoods.getStatus())) {
                criteria.andLike("status", "%" + seckillGoods.getStatus() + "%");
                //criteria.andStatusLike("%"+seckillGoods.getStatus()+"%");
            }
            if (StringUtils.isNotBlank(seckillGoods.getIntroduction())) {
                criteria.andLike("introduction", "%" + seckillGoods.getIntroduction() + "%");
                //criteria.andIntroductionLike("%"+seckillGoods.getIntroduction()+"%");
            }

        }
        List<TbSeckillGoods> all = seckillGoodsMapper.selectByExample(example);
        PageInfo<TbSeckillGoods> info = new PageInfo<TbSeckillGoods>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbSeckillGoods> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }

    @Override
    public void updateStatus(String status, Long[] ids) {
        //update tb_seckill_goods set stauts=? where id in ()
        TbSeckillGoods goods = new TbSeckillGoods();//更新后的数据
        goods.setStatus(status);
        Example exmpale = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = exmpale.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        seckillGoodsMapper.updateByExampleSelective(goods, exmpale);
    }

    @Override
    public Map getGoodsById(Long id) {
        Map result = new HashMap();
        TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).get(id);
        Integer stockCount = seckillGoods.getStockCount();//剩余库存
        long endTime = seckillGoods.getEndTime().getTime();
        long now = new Date().getTime();
        long haomiao = endTime - now;//毫秒数
        result.put("count", stockCount);
        result.put("time", haomiao);
        return result;
    }


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TbSeckillGoods> findAll() {
        List<TbSeckillGoods> values = redisTemplate.boundHashOps(SysConstants.SEC_KILL_GOODS).values();
        return values;
    }
}
