package com.pingyougou.service;
import com.pingyougou.pojos.TbSpecificationOption;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationOptionService extends CoreService<TbSpecificationOption> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbSpecificationOption> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbSpecificationOption> findPage(Integer pageNo, Integer pageSize, TbSpecificationOption SpecificationOption);
	
}
