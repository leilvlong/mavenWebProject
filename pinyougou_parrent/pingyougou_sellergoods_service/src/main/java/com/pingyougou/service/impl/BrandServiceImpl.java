package com.pingyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pingyougou.core.service.CoreServiceImpl;
import com.pingyougou.mapper.TbBrandMapper;
import com.pingyougou.pojos.TbBrand;
import com.pingyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class BrandServiceImpl extends CoreServiceImpl<TbBrand> implements BrandService {


    private TbBrandMapper tbBrandMapper;

    @Autowired
    public BrandServiceImpl(TbBrandMapper tbBrandMapper) {
        super(tbBrandMapper, TbBrand.class);
        this.tbBrandMapper = tbBrandMapper;
    }


    @Override
    public PageInfo<TbBrand> findPage(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(selectAll());
    }

    @Override
    public PageInfo findPage(Integer pageNum, Integer pageSize, TbBrand tb) {
        Example example = new Example(TbBrand.class);
        Example.Criteria criteria = example.createCriteria();
        if (tb != null){
            if (StringUtils.isNotBlank(tb.getName())){
                criteria.andLike("name","%"+tb.getName()+"%");
            }
            if(StringUtils.isNotBlank(tb.getFirstChar())){
                criteria.andEqualTo("firstChar",tb.getFirstChar());
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        List<TbBrand> tbBrands = tbBrandMapper.selectByExample(example);
        return new PageInfo(tbBrands);
    }


}
