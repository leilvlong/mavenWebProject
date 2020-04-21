package com.example.demo;

import com.example.demo.Dao.ESDao;
import com.example.demo.Dao.SQLDao;
import com.example.demo.parmters.TbItem;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void contextLoads() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();



        queryBuilder.withQuery(QueryBuilders.multiMatchQuery("三星", "title"));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("id").gte(1000000).lte(1100000));



        queryBuilder.withFilter(boolQueryBuilder);



        queryBuilder.withPageable(PageRequest.of(0, 10));

        NativeSearchQuery build = queryBuilder.build();

        List<TbItem> newTbItems = new ArrayList<>();

        AggregatedPage<TbItem> page = (AggregatedPage<TbItem>) elasticsearchTemplate.startScroll(1000 * 60, build, TbItem.class);
        String scrollId = page.getScrollId();



        newTbItems.addAll(page.getContent());
        boolean hasNext = true;
        while (hasNext){
            Page<TbItem> tbItems = elasticsearchTemplate.continueScroll(scrollId, 1000 * 60, TbItem.class);
            if (tbItems.hasContent()){
                newTbItems.addAll(tbItems.getContent());
            }else {
                hasNext = false;
            }
        }




        newTbItems.sort((TbItem item1, TbItem item2)->{
            return item1.getId().longValue()>item2.getId().longValue() ? 1:-1;
        });

        for (TbItem tbItem : newTbItems) {
            System.out.println(tbItem);
        }
    }

    @Autowired
    SQLDao sqlDao;

    @Autowired
    ESDao esDao;

    @Test
    public void importData(){
        List<TbItem> tbItems = sqlDao.selectAll();
        esDao.saveAll(tbItems);
    }

    @Test
    public void repositoryTest(){
        Iterator<TbItem> iterator = esDao.findAll().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }


}
