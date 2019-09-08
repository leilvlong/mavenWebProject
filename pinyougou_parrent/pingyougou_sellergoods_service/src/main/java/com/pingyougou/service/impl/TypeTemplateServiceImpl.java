package com.pingyougou.service.impl;
import java.util.List;
import java.util.Map;

import com.pingyougou.mapper.TbSpecificationOptionMapper;
import com.pingyougou.pojos.TbSpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pingyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pingyougou.mapper.TbTypeTemplateMapper;
import com.pingyougou.pojos.TbTypeTemplate;

import com.pingyougou.service.TypeTemplateService;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class TypeTemplateServiceImpl extends CoreServiceImpl<TbTypeTemplate>  implements TypeTemplateService {

	
	private TbTypeTemplateMapper typeTemplateMapper;
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	public TypeTemplateServiceImpl(TbTypeTemplateMapper typeTemplateMapper,TbSpecificationOptionMapper tbSpecificationOptionMapper ) {
		super(typeTemplateMapper, TbTypeTemplate.class);
		this.typeTemplateMapper=typeTemplateMapper;
		this.tbSpecificationOptionMapper = tbSpecificationOptionMapper;
	}

	
	

	
	@Override
    public PageInfo<TbTypeTemplate> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbTypeTemplate> all = typeTemplateMapper.selectAll();
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<TbTypeTemplate>(all);

        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbTypeTemplate> findPage(Integer pageNo, Integer pageSize, TbTypeTemplate typeTemplate) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbTypeTemplate.class);
        Example.Criteria criteria = example.createCriteria();

        if(typeTemplate!=null){			
						if(StringUtils.isNotBlank(typeTemplate.getName())){
				criteria.andLike("name","%"+typeTemplate.getName()+"%");
				//criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(StringUtils.isNotBlank(typeTemplate.getSpecIds())){
				criteria.andLike("specIds","%"+typeTemplate.getSpecIds()+"%");
				//criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(StringUtils.isNotBlank(typeTemplate.getBrandIds())){
				criteria.andLike("brandIds","%"+typeTemplate.getBrandIds()+"%");
				//criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(StringUtils.isNotBlank(typeTemplate.getCustomAttributeItems())){
				criteria.andLike("customAttributeItems","%"+typeTemplate.getCustomAttributeItems()+"%");
				//criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
        List<TbTypeTemplate> all = typeTemplateMapper.selectByExample(example);
        PageInfo<TbTypeTemplate> pageInfo = new PageInfo<TbTypeTemplate>(all);


		 List<TbTypeTemplate> redisAll = findAll();
		 if (redisAll != null){
			 System.out.println("缓存品牌");
			 for (TbTypeTemplate tbTypeTemplate : redisAll) {
				 String brandIds = tbTypeTemplate.getBrandIds();
				 List<Map> brandList = JSON.parseArray(brandIds, Map.class);
				 redisTemplate.boundHashOps("brandList").put(tbTypeTemplate.getId(),brandList);
				 List<Map> specList = findSpecList(tbTypeTemplate.getId());
				 redisTemplate.boundHashOps("specList").put(tbTypeTemplate.getId(),specList);
			 }
		 }


		 return pageInfo;
    }

	@Override
	public List<Map> findSpecList(Long id) {
		TbTypeTemplate tp = typeTemplateMapper.selectByPrimaryKey(id);
		String specIds = tp.getSpecIds();
		List<Map> maps = JSON.parseArray(specIds,Map.class);
		for (Map map : maps) {
			Long specId = Long.valueOf((Integer)map.get("id"));
			TbSpecificationOption tsfo = new TbSpecificationOption();
			tsfo.setSpecId(specId);
			List<TbSpecificationOption> options = tbSpecificationOptionMapper.select(tsfo);
			map.put("optionList",options);
		}
		return maps;
	}

}
