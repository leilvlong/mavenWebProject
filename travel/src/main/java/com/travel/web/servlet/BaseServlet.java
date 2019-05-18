package com.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.domain.ResultInfo;
import com.travel.web.handerl.CategoryHanderl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        String action = request.getParameter("action");
        try{
            Method requestHanding = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            requestHanding.setAccessible(true);
            info = (ResultInfo)requestHanding.invoke(this, request, response);
            if(info != null){
                infoTOJson(response,info);
            }


        }catch (Exception e){
            e.printStackTrace();
            info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
            infoTOJson(response,info);
        }
    }

    protected void infoTOJson(HttpServletResponse response, ResultInfo info ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(info);
        response.getWriter().write(jsonStr);

    }
}
