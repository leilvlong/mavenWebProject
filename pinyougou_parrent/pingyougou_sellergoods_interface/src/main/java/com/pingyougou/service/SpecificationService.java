package com.pingyougou.service;
import com.pingyougou.pojos.TbSpecification;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
import entity.Specification;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationService extends CoreService<TbSpecification> {
	
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	 PageInfo<TbSpecification> findPage(Integer pageNo, Integer pageSize);
	
	

	/**
	 * 分页
	 * @param pageNo 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	PageInfo<TbSpecification> findPage(Integer pageNo, Integer pageSize, TbSpecification Specification);

    void addTo(Specification specification);

    public Specification findOne(Long id);

    void update(Specification specification);

    void deleteTo(Long[] ids);
}
