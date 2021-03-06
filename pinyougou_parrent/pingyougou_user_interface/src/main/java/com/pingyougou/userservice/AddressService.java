package com.pingyougou.userservice;
import com.pingyougou.pojos.TbAddress;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AddressService extends CoreService<TbAddress> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbAddress> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbAddress> findPage(Integer pageNo, Integer pageSize, TbAddress Address);
	
}
