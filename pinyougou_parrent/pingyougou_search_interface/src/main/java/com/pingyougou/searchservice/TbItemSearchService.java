package com.pingyougou.searchservice;

import com.pingyougou.pojos.TbItem;

import java.util.List;
import java.util.Map;

public interface TbItemSearchService {
    Map<String, Object> search(Map<String, Object> searchMap);

    void deleteByIds(Long[] ids);

    void updateIndex(List<TbItem> tbItemListByIds);

}
