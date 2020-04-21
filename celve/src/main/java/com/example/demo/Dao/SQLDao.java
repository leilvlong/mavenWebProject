package com.example.demo.Dao;


import com.example.demo.parmters.TbItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface SQLDao extends Mapper<TbItem> {
}
