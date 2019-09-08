package com.pingyougou.service.impl;
import java.util.ArrayList;
import java.util.List;

import entity.Ids;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pingyougou.core.service.CoreServiceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import com.pingyougou.mapper.TbItemCatMapper;
import com.pingyougou.pojos.TbItemCat;

import com.pingyougou.service.ItemCatService;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl extends CoreServiceImpl<TbItemCat>  implements ItemCatService {

	
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	public ItemCatServiceImpl(TbItemCatMapper itemCatMapper) {
		super(itemCatMapper, TbItemCat.class);
		this.itemCatMapper=itemCatMapper;

	}

	
	

	
	@Override
    public PageInfo<TbItemCat> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbItemCat> all = itemCatMapper.selectAll();
        PageInfo<TbItemCat> info = new PageInfo<TbItemCat>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbItemCat> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbItemCat> findPage(Integer pageNo, Integer pageSize, TbItemCat itemCat) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbItemCat.class);
        Example.Criteria criteria = example.createCriteria();

        if(itemCat!=null){			
						if(StringUtils.isNotBlank(itemCat.getName())){
				criteria.andLike("name","%"+itemCat.getName()+"%");
				//criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
        List<TbItemCat> all = itemCatMapper.selectByExample(example);
        PageInfo<TbItemCat> info = new PageInfo<TbItemCat>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbItemCat> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }

    @Override
    public List<TbItemCat> findByParrent(Long pparrentId) {

        List<TbItemCat> redisAll = findAll();
        if (redisAll != null){
            System.out.println("执行缓存");
            for (TbItemCat tbItemCat : redisAll) {
                redisTemplate.boundHashOps("itemCat").put(tbItemCat.getName(),tbItemCat.getTypeId());
            }
        }

        TbItemCat itemCat = new TbItemCat();
        itemCat.setParentId(pparrentId);
        return itemCatMapper.select(itemCat);
    }



    public void delete(Ids ids) {
        List<Long> id = ids.getId();
        ArrayList<Long> integers = new ArrayList<>();
        integers.addAll(id);
        if (ids.getGrade() == 1){
            for (Long integer : id) {
                List<TbItemCat> byParrent = findByParrent(integer);
                for (TbItemCat tbItemCat : byParrent) {
                    List<TbItemCat> byParrent1 = findByParrent(tbItemCat.getId());
                    integers.add(tbItemCat.getId());
                    for (TbItemCat itemCat : byParrent1) {
                        integers.add(itemCat.getId());
                    }
                }
            }
        }else if (ids.getGrade() == 2){
            for (Long integer : id) {
                List<TbItemCat> byParrent = findByParrent(integer);
                for (TbItemCat tbItemCat : byParrent) {
                    integers.add(tbItemCat.getId());
                }
            }
        }
        delete(integers.toArray());
    }



}
