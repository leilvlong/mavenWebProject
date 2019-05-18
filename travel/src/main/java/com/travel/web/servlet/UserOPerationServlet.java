package com.travel.web.servlet;

import com.travel.constant.Constant;
import com.travel.domain.ResultInfo;
import com.travel.domain.User;
import com.travel.exception.UserActiveError;
import com.travel.exception.UserPasswordError;
import com.travel.exception.UserUsernameError;
import com.travel.factory.BeanFactory;
import com.travel.service.IUserService;
import com.travel.utils.Md5Util;
import com.travel.utils.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.UUID;

@WebServlet("/user")
public class UserOPerationServlet extends BaseServlet {
    private IUserService us = (IUserService) BeanFactory.getBean("userServiceImpl");

    private ResultInfo checkUsername(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        User user = us.findUserByUsername(username);
        ResultInfo info = new ResultInfo();
        if(user == null){
            info.setFlag(true);
            info.setData(true);
        }else{
            info.setFlag(true);
            info.setData(false);
        }
        return info;
    }

    private ResultInfo active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        try{
            us.userActive(code);
            response.sendRedirect("login.html");
        }catch (Exception e){
            response.getWriter().write(e.getMessage());
        }
        return null;
    }

    private ResultInfo register(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo();
        User user = new User();
        Map<String, String[]> userMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,userMap);
            String password = user.getPassword();
            // 密码加密 设置code 用户尚未激活
            String md5Pssword = Md5Util.encodeByMd5(password);
            user.setPassword(md5Pssword);
            user.setCode(UuidUtil.getUuid());
            user.setStatus(Constant.UNACTIVE);

            boolean flag = us.registerUser(user);
            info.setFlag(flag);
        } catch (Exception e) {
            info.setFlag(false);
            info.setData(null);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    private ResultInfo userLogin(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String clientCode = request.getParameter("code");
        String servletCode = (String) session.getAttribute(Constant.CHECKCODE_KEY);
        ResultInfo info = new ResultInfo();
        info.setFlag(false);
        info.setData(false);

        if(servletCode.equalsIgnoreCase(clientCode)) {
            try{
                User user = us.userLogin(username, password);
                session.setAttribute(Constant.USER_KEY,user);
                info.setFlag(true);
                info.setData(true);
            }catch (UserUsernameError | UserActiveError | UserPasswordError e){
                info.setErrorMsg(e.getMessage());
            }
        }else{
            info.setErrorMsg("验证码错误");
        }
        return info;
    }

    private ResultInfo getUserInfo(HttpServletRequest request,HttpServletResponse response){
        ResultInfo info = new ResultInfo();
        HttpSession session = request.getSession();
        try{
            User user = (User) session.getAttribute(Constant.USER_KEY);
            info.setFlag(true);
            info.setData(user);
        }catch (Exception e){
        }
        return info;
    }

    private ResultInfo loginOut(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        try {
            response.sendRedirect("login.html");
        } catch (IOException e) {
        }
        return null;
    }


}
