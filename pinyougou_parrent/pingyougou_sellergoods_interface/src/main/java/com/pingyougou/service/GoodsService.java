package com.pingyougou.service;
import java.util.List;
import com.pingyougou.pojos.TbGoods;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
import com.pingyougou.pojos.TbItem;
import entity.Goods;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsService extends CoreService<TbGoods> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbGoods> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbGoods> findPage(Integer pageNo, Integer pageSize, TbGoods Goods);

	void add(Goods goods);

	void update(Goods goods);

	Goods findOne(Long id);

	void updateStatus(Long[] ids, String status);

    List<TbItem> findTbItemListByIds(Long[] ids);
}
