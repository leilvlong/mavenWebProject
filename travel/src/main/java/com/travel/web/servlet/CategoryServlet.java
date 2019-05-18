package com.travel.web.servlet;

import com.travel.domain.ResultInfo;
import com.travel.factory.BeanFactory;
import com.travel.service.ICategoryService;
import com.travel.web.handerl.CategoryHanderl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Proxy;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private ICategoryService cs = (ICategoryService) BeanFactory.getBean("categoryServiceImpl");

    private ICategoryService cs2 = (ICategoryService) Proxy.newProxyInstance(CategoryHanderl.class.getClassLoader(),
            new Class[]{ICategoryService.class},
            new CategoryHanderl((ICategoryService) BeanFactory.getBean("categoryServiceImpl")));


    private Object findIndexCategory(HttpServletRequest request, HttpServletResponse response){
        /*ResultInfo info = new ResultInfo();
        try{
            String indexCategory = cs.findIndexCategory();
            info.setFlag(true);
            info.setData(indexCategory);
        }catch (Exception e){
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }*/
        ResultInfo info = new ResultInfo();
        info = cs2.findIndexCategory();
        return info;

    }
}
