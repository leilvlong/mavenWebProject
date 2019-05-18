package com.travel.service;

import com.travel.domain.PageBean;
import com.travel.domain.Route;

import java.util.List;

public interface IRouteService {
    public String findIndexRoute();

    public PageBean findCategoryRoute(String cid,String keyword, String currentPage);

    public Route findRouteDetail(String rid);
}
