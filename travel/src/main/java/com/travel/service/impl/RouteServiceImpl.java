package com.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.constant.Constant;
import com.travel.dao.IRouteDao;
import com.travel.domain.*;
import com.travel.factory.BeanFactory;
import com.travel.service.IRouteService;
import com.travel.utils.JedisContentPoolUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements IRouteService {
    private IRouteDao rd = (IRouteDao) BeanFactory.getBean("routeDaoImpl");
    private Jedis jedis = JedisContentPoolUtil.getPoolJedisResoures();


    @Override
    public String findIndexRoute() {
        String indexRoutes = jedis.get(Constant.INDEX_ROUTE);
        if (indexRoutes == null){
            Map<String, List<Route>> routeMap = new HashMap<>();
            List<Route> popList = rd.routePopList();
            List<Route> newestList = rd.routenewestList();
            List<Route> themeList = rd.themeList();
            routeMap.put("popList",popList);
            routeMap.put("newestList",newestList);
            routeMap.put("themeList",themeList);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                indexRoutes = objectMapper.writeValueAsString(routeMap);
                jedis.set(Constant.INDEX_ROUTE,indexRoutes);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return indexRoutes;
    }

    @Override
    public PageBean findCategoryRoute(String cid,String keyword, String currentPage) {
        PageBean pageBean = new PageBean();
        // 1.设置数据总条数

        pageBean.setTotalCount(rd.getRouteCount(cid,keyword));

        //2.设置每页条数  3.设置总页数
        pageBean.setPageSize(Constant.PAGESIZE);
        //3. 设置currentPage
        Integer page = Integer.parseInt(currentPage);
        pageBean.setCurrentPage(page);
        //4 设置数据
        List<Route> categoryRoute = rd.findSearch(cid,keyword,page,Constant.PAGESIZE);
        pageBean.setDataList(categoryRoute);


        return pageBean;
    }

    @Override
    public Route findRouteDetail(String rid) {

        Route routeDetail = rd.findRouteDetail(rid);
        List<RouteImg> routeImg = rd.getRouteImg(rid);
        routeDetail.setRouteImgList(routeImg);

        return routeDetail;
    }

    public static void main(String[] args) {
        RouteServiceImpl routeService = new RouteServiceImpl();
        Route routeDetail = routeService.findRouteDetail("1");
        Category category = routeDetail.getCategory();
        Seller seller = routeDetail.getSeller();
        System.out.println(category.getCname());
        System.out.println(seller.getSname());
        List<RouteImg> routeImgList = routeDetail.getRouteImgList();
        for (RouteImg routeImg : routeImgList) {
            System.out.println(routeImg.getBigPic());
        }

    }
}
