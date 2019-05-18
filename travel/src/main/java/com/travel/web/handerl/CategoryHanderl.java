package com.travel.web.handerl;

import com.travel.constant.Constant;
import com.travel.domain.ResultInfo;
import com.travel.service.ICategoryService;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*
CategoryService 委托承接对象:
    服务器分为三成架构 servlet层 service层, dao层
    跑servlet流程时发现对于service层的异常处理是千篇一律的以至于都懒得处理了
    突然想到动态代理AOP 于是决定对分类信息这块采用该处理方式
    实际使用是可行的,由service返回已经设置过data的ResultInfo
    然后由该承接委托对象处理可能出现的service异常
    其他的业务功能模块懒得改了(写这个servlet只是为了体验原生的三层架构)
 */


public class CategoryHanderl implements InvocationHandler {

    private ICategoryService category;

    public CategoryHanderl(ICategoryService category) {
        this.category = category;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ResultInfo info = null;
        try{
            info = (ResultInfo) method.invoke(category, args);
            info.setFlag(true);
        }catch (Exception e){
            info = new ResultInfo();
            info.setFlag(false);
            info.setData(false);
            info.setErrorMsg(Constant.SERVLET_ERROR);

        }
        return info;
    }
}
