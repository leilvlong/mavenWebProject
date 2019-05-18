package com.travel.dao.impl;

import com.travel.dao.IRouteDao;
import com.travel.domain.*;
import com.travel.utils.JdbcUtil;
import com.travel.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteDaoImpl implements IRouteDao {
    private JdbcTemplate jt = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public List<Route> routePopList() {
        String sql = "select * from tab_route where rflag=1 order by count desc limit 0,4";
        List<Route> routes = new ArrayList<>();
        try {
            routes = jt.query(sql,new BeanPropertyRowMapper<>(Route.class));
        }catch (Exception e){
        }
        return routes;
    }

    @Override
    public List<Route> routenewestList() {
        String sql = "select * from tab_route where rflag=1 order by rdate desc limit 0,4";
        List<Route> routes = new ArrayList<>();
        try {
            routes = jt.query(sql,new BeanPropertyRowMapper<>(Route.class));
        }catch (Exception e){
        }
        return routes;
    }

    @Override
    public List<Route> themeList() {
        String sql = "select * from tab_route where rflag=1 and isThemeTour=1 limit 0,4";
        List<Route> routes = new ArrayList<>();
        try {
            routes = jt.query(sql,new BeanPropertyRowMapper<>(Route.class));
        }catch (Exception e){
        }
        return routes;
    }

    @Override
    public Integer getRouteCount(String cid,String keyword) {
        String sql = "select count(*) from tab_route where rflag=1";
        List<Object> partmers  =  new ArrayList<>();
        if (!StringUtil.isEmpty(cid)){
            sql+= " and cid=?";
            partmers.add(cid);
        }
        if (!StringUtil.isEmpty(keyword)){
            sql += " and rname like ?";
            partmers.add("%"+keyword+"%");
        }


        Integer count = 0;
        try{
            count = jt.queryForObject(sql, Integer.class,partmers.toArray() );
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Route> findCategoryRoute(String cid, Integer currentPage,Integer pageSize) {
        String sql = "select * from tab_route where rflag=1 and cid=? limit ?,?";
        List<Route> routes = new ArrayList<>();
        try {

            routes = jt.query(sql,new BeanPropertyRowMapper<>(Route.class),
                    cid,(currentPage-1)*pageSize,pageSize);
        }catch (Exception e){
        }
        return routes;
    }

    @Override
    public List<Route> findSearch(String cid, String keyword, Integer currentPage, Integer pageSize) {


        String sql = "select * from tab_route where rflag=1";
        List<Object> partems = new ArrayList<>();
        if (!StringUtil.isEmpty(cid)) {
            sql+=" and cid=?";
            partems.add(cid);
        }
        if (! StringUtil.isEmpty(keyword)){
            sql+=" and rname like?";
            partems.add("%"+keyword+"%");
        }
        sql += " limit ?,?";
        partems.add((currentPage-1)*pageSize);
        partems.add(pageSize);
        List<Route> routes = null;
        try{
            routes = jt.query(sql, new BeanPropertyRowMapper<>(Route.class), partems.toArray());
        }catch (Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    public Route findRouteDetail(String rid) {
        String sql ="SELECT * FROM tab_route,tab_category,tab_seller where tab_route.rflag=1 and tab_route.cid=tab_category.cid and tab_route.sid=tab_seller.sid and tab_route.rid=?";


        Route route = new Route();
        Category category = new Category();
        Seller seller = new Seller();
        try {
            Map<String, Object> stringObjectMap = jt.queryForMap(sql,rid);
            BeanUtils.populate(route,stringObjectMap);
            BeanUtils.populate(category,stringObjectMap);
            BeanUtils.populate(seller,stringObjectMap);
            route.setCategory(category);
            route.setSeller(seller);
        } catch (Exception e) {
        }
        return route;
    }

    @Override
    public List<RouteImg> getRouteImg(String rid) {
        String sql = "SELECT * FROM tab_route_img where rid=?";
        List<RouteImg> routeImgs = null;
        try{
            routeImgs = jt.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
        }catch (Exception e){
        }
        return routeImgs;
    }


    public static void main(String[] args) {
        RouteDaoImpl routeDao = new RouteDaoImpl();
        List<RouteImg> routeImg = routeDao.getRouteImg("1");
        for (RouteImg img : routeImg) {
            System.out.println(img.getBigPic());
        }
    }
}

