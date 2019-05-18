package com.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.constant.Constant;
import com.travel.dao.ICategoryDao;
import com.travel.domain.Category;
import com.travel.domain.ResultInfo;
import com.travel.factory.BeanFactory;
import com.travel.service.ICategoryService;
import com.travel.utils.JedisContentPoolUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private ICategoryDao cd = (ICategoryDao) BeanFactory.getBean("categoryDaoImpl");

    @Override
    public ResultInfo findIndexCategory(){
        ResultInfo info = new ResultInfo();
        Jedis jedis = JedisContentPoolUtil.getPoolJedisResoures();
        String categoryJson = jedis.get(Constant.CATEGORY_KEY);
        if (categoryJson == null){
            List<Category> indexCategory = cd.findIndexCategory();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                categoryJson = objectMapper.writeValueAsString(indexCategory);
                jedis.set(Constant.CATEGORY_KEY,categoryJson);
            } catch (JsonProcessingException e) {
            }
        }
        info.setData(categoryJson);
        return info;
    }

}
