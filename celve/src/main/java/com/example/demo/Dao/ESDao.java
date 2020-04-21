package com.example.demo.Dao;

import com.example.demo.parmters.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ESDao extends ElasticsearchRepository<TbItem,Long> {

}
