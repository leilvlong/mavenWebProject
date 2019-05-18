package com.travel.web.servlet;

import com.travel.domain.PageBean;
import com.travel.domain.ResultInfo;
import com.travel.domain.Route;
import com.travel.factory.BeanFactory;
import com.travel.service.IRouteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {
    private IRouteService rs = (IRouteService) BeanFactory.getBean("routeServiceImpl");

    public ResultInfo findIndexRoute(HttpServletRequest request, HttpServletResponse response){
        ResultInfo info = new ResultInfo();

        try{
            String indexRoute = rs.findIndexRoute();
            info.setFlag(true);
            info.setData(indexRoute);
        }catch (Exception e){
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    public ResultInfo findCategoryRoute(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo();
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String keyword = request.getParameter("keyword");
        try {

            PageBean categoryRoute = rs.findCategoryRoute(cid, keyword, currentPage);
            info.setFlag(true);
            info.setData(categoryRoute);
        }catch (Exception e){
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }

        return info;
    }

    private ResultInfo findRouteDetail(HttpServletRequest request, HttpServletResponse response){
        ResultInfo info = new ResultInfo();
        String rid = request.getParameter("rid");

        try{
            Route routeDetail = rs.findRouteDetail(rid);
            info.setFlag(true);
            info.setData(routeDetail);
        }catch (Exception e){
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

}
