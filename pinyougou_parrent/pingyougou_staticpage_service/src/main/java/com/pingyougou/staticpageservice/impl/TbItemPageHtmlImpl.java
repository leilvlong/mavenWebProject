package com.pingyougou.staticpageservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pingyougou.mapper.TbGoodsDescMapper;
import com.pingyougou.mapper.TbGoodsMapper;
import com.pingyougou.mapper.TbItemCatMapper;
import com.pingyougou.mapper.TbItemMapper;
import com.pingyougou.pojos.TbGoods;
import com.pingyougou.pojos.TbGoodsDesc;
import com.pingyougou.pojos.TbItem;
import com.pingyougou.pojos.TbItemCat;
import com.pingyougou.staticpageservice.TbItemPageHtml;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbItemPageHtmlImpl implements TbItemPageHtml {
    @Autowired
    private TbGoodsMapper tbGoodsMapper;


    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Value("${pageDir}")
    private String pageDir;

    @Override
    public void genItemHtml(Long id) {
        //1.根据PUS的ID 获取 SPU的数据
        TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
        //2.使用freemarker生成静态页（模板 + 数据集=html）
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        //3.生成数据填充的静态html页面
        genHTML("item.ftl",tbGoods,tbGoodsDesc);

    }

    private void genHTML(String templateName, TbGoods tbGoods, TbGoodsDesc tbGoodsDesc) {
        FileWriter writerHtml = null;
        try{
            //1.创建configruation 配置文件中已经设置模板目录与编码
            Configuration configuration = configurer.getConfiguration();

            //2.设置模板数据
            //2.1 设置基础数据
            /*
            tbGoods
            tbGoodsDesc
            cat1
            cat2
            cat3
            skuList
             */

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("tbGoods",tbGoods);
            dataMap.put("tbGoodsDesc",tbGoodsDesc);

            //2.2设置分类数据
            TbItemCat cat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());
            TbItemCat cat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());
            TbItemCat cat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
            dataMap.put("cat1",cat1.getName());
            dataMap.put("cat2",cat2.getName());
            dataMap.put("cat3",cat3.getName());

            //2.3 设置对应的商品数据
            Example example = new Example(TbItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("goodsId",tbGoods.getId());
            criteria.andEqualTo("status","1");
            List<TbItem> skulist = itemMapper.selectByExample(example);
            dataMap.put("skuList",skulist);

            Template template = configuration.getTemplate(templateName);
            writerHtml = new FileWriter(new File(pageDir+tbGoods.getId()+".html"));
            template.process(dataMap,writerHtml);
        }catch (Exception e){

        }finally {
            if (writerHtml != null) {
                try {
                    writerHtml.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
