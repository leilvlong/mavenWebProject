package com.pingyougou.importdata.dao;

import com.pingyougou.pojos.TbItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ImportDataDao extends ElasticsearchRepository<TbItem,Long> {

}
