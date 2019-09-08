package com.pingyougou.seckillservice;
import java.util.List;
import com.pingyougou.pojos.TbSeckillOrder;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SeckillOrderService extends CoreService<TbSeckillOrder> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbSeckillOrder> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbSeckillOrder> findPage(Integer pageNo, Integer pageSize, TbSeckillOrder SeckillOrder);

	/**
	 * 下秒杀订单到redis中
	 * @param id
	 * @param userId
	 */
    void submitOrder(Long id, String userId);

    TbSeckillOrder findOrderByUserId(String userId);


	void updateOrderStatus(String userId, String transaction_id);


	void deleteOrder(String userId);

}
