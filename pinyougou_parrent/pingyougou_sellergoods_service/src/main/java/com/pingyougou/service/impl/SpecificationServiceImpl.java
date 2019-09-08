package com.pingyougou.service.impl;
import java.util.List;

import com.pingyougou.mapper.TbSpecificationOptionMapper;
import com.pingyougou.pojos.TbSpecificationOption;
import entity.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pingyougou.core.service.CoreServiceImpl;

import tk.mybatis.mapper.entity.Example;

import com.pingyougou.mapper.TbSpecificationMapper;
import com.pingyougou.pojos.TbSpecification;

import com.pingyougou.service.SpecificationService;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl extends CoreServiceImpl<TbSpecification>  implements SpecificationService {

	
	private TbSpecificationMapper specificationMapper;

	private TbSpecificationOptionMapper tbSpecificationOptionMapper;

	@Autowired
	public SpecificationServiceImpl(TbSpecificationMapper specificationMapper, TbSpecificationOptionMapper tbSpecificationOptionMapper) {
		super(specificationMapper, TbSpecification.class);
		this.specificationMapper=specificationMapper;
		this.tbSpecificationOptionMapper = tbSpecificationOptionMapper;
	}

	
	

	
	@Override
    public PageInfo<TbSpecification> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbSpecification> all = specificationMapper.selectAll();
        PageInfo<TbSpecification> pageInfo = new PageInfo<TbSpecification>(all);

        //序列化再反序列化
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbSpecification> findPage(Integer pageNo, Integer pageSize, TbSpecification specification) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();

        if(specification!=null){			
						if(StringUtils.isNotBlank(specification.getSpecName())){
				criteria.andLike("specName","%"+specification.getSpecName()+"%");
				//criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
        List<TbSpecification> all = specificationMapper.selectByExample(example);
        PageInfo<TbSpecification> pageInfo = new PageInfo<TbSpecification>(all);
        return pageInfo;
    }

    @Override
    public void addTo(Specification specification) {

        TbSpecification ts = specification.getSpecification();
        specificationMapper.insert(ts);
        List<TbSpecificationOption> tsos = specification.getOptionList();
        for (TbSpecificationOption tso : tsos) {
            tso.setSpecId(ts.getId());
            tbSpecificationOptionMapper.insert(tso);

        }
    }

    @Override
    public Specification findOne(Long id) {
        TbSpecification ts = specificationMapper.selectByPrimaryKey(id);
        TbSpecificationOption tso = new TbSpecificationOption();
        tso.setSpecId(id);
        List<TbSpecificationOption> select = tbSpecificationOptionMapper.select(tso);
        Specification specification = new Specification();
        specification.setSpecification(ts);
        specification.setOptionList(select);
        return specification;
    }

    @Override
    public void update(Specification specification) {
        TbSpecification ts = specification.getSpecification();
        specificationMapper.updateByPrimaryKey(ts);

        TbSpecificationOption tbso = new TbSpecificationOption();
        tbso.setSpecId(ts.getId());
        tbSpecificationOptionMapper.delete(tbso);

        List<TbSpecificationOption> tsos = specification.getOptionList();
        for (TbSpecificationOption tso : tsos) {
            tso.setSpecId(ts.getId());
            tbSpecificationOptionMapper.insert(tso);
        }

    }

    @Override
    public void deleteTo(Long[] ids) {
        for (Long id : ids) {
            TbSpecificationOption tbso = new TbSpecificationOption();
            tbso.setSpecId(id);
            tbSpecificationOptionMapper.delete(tbso);
        }
        delete(ids);

    }

}
