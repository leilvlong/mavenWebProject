package com.pingyougou.searchservice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pingyougou.pojos.TbItem;
import com.pingyougou.searchservice.TbItemSearchService;
import com.pingyougou.searchservice.dao.ItemDao;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@Service
public class TbItemSearchServiceImpl implements TbItemSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String,Object> resultMap = new HashMap<>();

        //  1.获取查询关键字
        String keywords = (String) searchMap.get("keywords");

        //  2.构建查询对象,通过查询对象构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //  2.1 构建查询条件与字段
        queryBuilder.withQuery(QueryBuilders.multiMatchQuery(keywords,"title","seller","brand","category"));
        //  2.2 构建分组查询
        queryBuilder.addAggregation(AggregationBuilders.terms("category_group").field("category").size(50));
        //  2.3 构建高亮
        queryBuilder.
                withHighlightFields(new HighlightBuilder.Field("title")).
                withHighlightBuilder(new HighlightBuilder().preTags("<em style=\"color:red\">").postTags("</em>"));

        //  3 过滤查询 分类过滤查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String category = (String)searchMap.get("category");
        if (StringUtils.isNotBlank(category)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("category",category));
        }
        //  3.1 品牌过滤查询
        String brand = (String)searchMap.get("brand");
        if (StringUtils.isNotBlank(category)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("brand",brand));
        }
        //  3.2规格过滤查询
        Map<String,String> spec = (Map<String, String>) searchMap.get("spec");
        if (spec != null){
            for (String key : spec.keySet()) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("specMap"+key+".keyword",spec.get(key)));
            }
        }

        //  3.3 价格区间查询
        String price = (String) searchMap.get("price");
        if (StringUtils.isNotBlank(price)){
            String[] split = price.split("-");
            if ("*".equals(split[1])){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]));
            }else{
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(split[0],true).to(split[1],true));
            }
        }




        //  4,.设置过滤查询并创建查询对象
        NativeSearchQuery searchQuery = queryBuilder.withFilter(boolQueryBuilder).build();

        //  5. 设置分页
        Integer pageNo = (Integer) searchMap.get("pageNo");
        Integer pageSize = (Integer) searchMap.get("pageSize");
        pageNo =  pageNo == null ? 1 : pageNo;
        pageSize =  pageSize == null ? 1 : pageSize;
        searchQuery.setPageable(PageRequest.of(pageNo,pageSize));

        //  5.1 设置排序
        String sortField = (String) searchMap.get("sortField");
        String sortType = (String) searchMap.get("sortType");
        if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortType)){
            if(sortType.equals("ASC")) {
                Sort sort = new Sort(Sort.Direction.ASC, sortField);
                searchQuery.addSort(sort);
            }else if(sortType.equals("DESC")){
                Sort sort = new Sort(Sort.Direction.DESC, sortField);
                searchQuery.addSort(sort);
            }else{
                System.out.println("不排序");
            }
        }


        //  6.执行查询
        AggregatedPage<TbItem> tbItems = elasticsearchTemplate.queryForPage(searchQuery, TbItem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<T> content = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();
                if (hits == null || hits.getTotalHits() <= 0) {
                    return new AggregatedPageImpl<>(content);
                }
                for (SearchHit hit : hits) {
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField highlightField = highlightFields.get("title");

                    String sourceAsString = hit.getSourceAsString();
                    TbItem tbItem = JSON.parseObject(sourceAsString, TbItem.class);
                    if (highlightField != null) {
                        Text[] fragments = highlightField.getFragments();
                        if (fragments != null) {
                            StringBuilder sb = new StringBuilder();
                            for (Text fragment : fragments) {
                                sb.append(fragment.string());
                            }
                            tbItem.setTitle(sb.toString());//有高亮
                            content.add((T) tbItem);
                        }
                    } else {
                        content.add((T) tbItem);
                    }
                }
                return new AggregatedPageImpl<T>(content, pageable, hits.getTotalHits(), searchResponse.getAggregations(), searchResponse.getScrollId());
            }
        });

        //  7.获取分组结果
        StringTerms category_group = (StringTerms) tbItems.getAggregation("category_group");
        ArrayList<String> categoryList = new ArrayList<>();
        if (category_group != null){
            List<StringTerms.Bucket> buckets = category_group.getBuckets();
            for (StringTerms.Bucket bucket : buckets) {
                categoryList.add(bucket.getKeyAsString());
            }
        }

        if(StringUtils.isNotBlank(category)){
            Map map = searchBrandAndSpecList(category);//{ "brandList",[],"specList":[]}
            resultMap.putAll(map);
        }else {
            //否则 查询默认的商品分类下的品牌和规格的列表
            if(categoryList!=null && categoryList.size()>0) {
                Map map = searchBrandAndSpecList(categoryList.get(0));//{ "brandList",[],"specList":[]}
                resultMap.putAll(map);
            }else{
                resultMap.put("specList", new HashMap<>());
                resultMap.put("brandList",new HashMap<>());
            }

        }

        //8.设置结果集到map 返回(总页数 总记录数 当前页的集合 ......)
        resultMap.put("total",tbItems.getTotalElements());
        resultMap.put("rows",tbItems.getContent());//当前页的集合
        resultMap.put("totalPages",tbItems.getTotalPages());//总页数
        resultMap.put("categoryList",categoryList);//商品分类的列表数据
        System.out.println(resultMap);
        return resultMap;

    }


    @Autowired
    private ItemDao itemDao;

    @Override
    public void deleteByIds(Long[] ids) {
        System.out.println(Arrays.toString(ids));
        DeleteQuery query = new DeleteQuery();
        query.setQuery(QueryBuilders.termsQuery("goodsId",ids));
        elasticsearchTemplate.delete(query,TbItem.class);
    }

    @Override
    public void updateIndex(List<TbItem> tbItemListByIds) {
        for (TbItem tbItem : tbItemListByIds) {
            String spec = tbItem.getSpec();
            Map map = JSON.parseObject(spec, Map.class);
            tbItem.setSpecMap(map);
        }
        itemDao.saveAll(tbItemListByIds);
    }


    private Map searchBrandAndSpecList(String category){
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        Object specList = redisTemplate.boundHashOps("specList").get(typeId);
        Object brandList = redisTemplate.boundHashOps("brandList").get(typeId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("specList",specList);
        map.put("brandList",brandList);
        return map;
    }

}
