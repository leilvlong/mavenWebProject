package com.pingyougou.searchservice.dao;

import com.pingyougou.pojos.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemDao extends ElasticsearchRepository<TbItem,Long> {
}
