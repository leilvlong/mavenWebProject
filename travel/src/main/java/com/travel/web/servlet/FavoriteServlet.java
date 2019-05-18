package com.travel.web.servlet;

import com.travel.constant.Constant;
import com.travel.domain.Favorite;
import com.travel.domain.ResultInfo;
import com.travel.domain.User;
import com.travel.factory.BeanFactory;
import com.travel.service.IFavotiteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet {
    private IFavotiteService fs = (IFavotiteService) BeanFactory.getBean("favoriteServiceImpl");

    private ResultInfo isFavorite(HttpServletRequest request, HttpServletResponse response){
        ResultInfo info = new ResultInfo();
        String rid = request.getParameter("rid");
        try{
            User user = (User) request.getSession().getAttribute(Constant.USER_KEY);
            if(user == null){
                info.setFlag(true);
                info.setData(false);
            }else{
                Favorite favorite = fs.findUserFavorite(rid, user);
                if(favorite != null){
                    info.setFlag(true);
                    info.setData(true);
                }else{
                    info.setFlag(true);
                    info.setData(false);
                }
            }
        }catch (Exception e){
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    private ResultInfo addFavorite(HttpServletRequest request, HttpServletResponse response){
        ResultInfo info = new ResultInfo();
        String rid = request.getParameter("rid");
        try{
            User user = (User) request.getSession().getAttribute(Constant.USER_KEY);
            if(user == null){
                info.setFlag(true);
                info.setData(Constant.TRUE);
            }else{
                Integer count = fs.addFavorite(rid, user);
                info.setFlag(true);
                info.setData(count);
            }
        }catch (Exception e){
            info.setFlag(false);
            info.setData(Constant.FALSE);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

}
