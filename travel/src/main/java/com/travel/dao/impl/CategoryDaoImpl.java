package com.travel.dao.impl;

import com.travel.dao.ICategoryDao;
import com.travel.domain.Category;
import com.travel.domain.Route;
import com.travel.utils.JdbcUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements ICategoryDao {
    private JdbcTemplate jt = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public List<Category> findIndexCategory(){
        System.out.println("dao");
        String sql = "select * from tab_category";
        List<Category> categories = null;
        try{
            categories = jt.query(sql, new BeanPropertyRowMapper<>(Category.class));
        }catch (Exception e){
        }
        return categories;
    }


    public static void main(String[] args) {
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        System.out.println(categoryDao.findIndexCategory());
    }


}
