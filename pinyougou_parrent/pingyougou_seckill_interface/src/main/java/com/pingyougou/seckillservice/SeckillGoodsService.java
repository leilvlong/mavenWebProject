package com.pingyougou.seckillservice;
import java.util.List;
import java.util.Map;

import com.pingyougou.pojos.TbSeckillGoods;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SeckillGoodsService extends CoreService<TbSeckillGoods> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbSeckillGoods> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbSeckillGoods> findPage(Integer pageNo, Integer pageSize, TbSeckillGoods SeckillGoods);

    void updateStatus(String status, Long[] ids);

	/**
	 * 根据商品的ID 从redis中获取商品的剩余库存和倒计时时间
	 * @param id
	 * @return
	 */
	Map getGoodsById(Long id);
}
