package com.pingyougou.importdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.pingyougou.importdata.dao.ImportDataDao;
import com.pingyougou.importdata.service.ImportServic;
import com.pingyougou.mapper.TbItemMapper;
import com.pingyougou.pojos.TbItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ImportServicImpl implements ImportServic {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private ImportDataDao dataDao;

    @Override
    public void importData() {
        TbItem condition = new TbItem();
        condition.setStatus("1");
        List<TbItem> itemList = itemMapper.select(condition);
        for (TbItem tbItem : itemList) {
            String spec = tbItem.getSpec();
            Map map = JSON.parseObject(spec, Map.class);
            tbItem.setSpecMap(map);
        }
        dataDao.saveAll(itemList);
    }
}
