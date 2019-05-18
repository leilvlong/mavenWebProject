package com.travel.dao;

import com.travel.domain.PageBean;
import com.travel.domain.Route;
import com.travel.domain.RouteImg;

import java.util.List;

public interface IRouteDao {

    public List<Route> routePopList();
    public List<Route> routenewestList();
    public List<Route> themeList();
    public Integer getRouteCount(String cid, String keyword);
    public List<Route> findCategoryRoute(String cid, Integer currentPage, Integer pageSize);
    public List<Route> findSearch(String cid,String keyword, Integer currentPage, Integer pageSize);
    public Route findRouteDetail(String rid);
    public List<RouteImg> getRouteImg(String rid);
}
