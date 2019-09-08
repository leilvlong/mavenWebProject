package com.pingyougou.service;

import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreService;
import com.pingyougou.pojos.TbBrand;

public interface BrandService extends CoreService<TbBrand> {

    PageInfo<TbBrand> findPage(Integer pageNum, Integer pageSize);
    
    PageInfo findPage(Integer pageNum, Integer pageSize, TbBrand tb);
}
