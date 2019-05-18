package com.travel.service.impl;

import com.travel.constant.Constant;
import com.travel.dao.IUserDao;
import com.travel.domain.User;
import com.travel.exception.UserActiveError;
import com.travel.exception.UserPasswordError;
import com.travel.exception.UserUsernameError;
import com.travel.factory.BeanFactory;
import com.travel.service.IUserService;
import com.travel.utils.MailUtil;
import com.travel.utils.Md5Util;

public class UserServiceImpl implements IUserService {
    private IUserDao ud = (IUserDao) BeanFactory.getBean("userDaoImpl");


    @Override
    public User findUserByUsername(String username) {
        return ud.findUserByUsername(username);
    }

    @Override
    public boolean registerUser(User user) {
        boolean flag = ud.registerUser(user);
        if (flag){
            try {
                MailUtil.sendMail(user.getEmail(),"欢迎"+user.getName()+"注册黑马旅游网,点击<a href='http://localhost:8080/travel/user?action=active&code="+user.getCode()+"'>激活</a>");
            } catch (Exception e) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public void userActive(String code) {
        User user = ud.userActive(code);
        if(user != null){
            if(user.getStatus().equals(Constant.UNACTIVE)){
                user.setStatus(Constant.ACTIVED);
                ud.updateUser(user);
            }else{
                throw  new RuntimeException("用户已激活");
            }
        }else{
            throw new RuntimeException("激活码异常");
        }
    }

    @Override
    public User userLogin(String username, String password) throws UserActiveError, UserPasswordError, UserUsernameError {
        User user = ud.findUserByUsername(username);

        if(user != null){
            try {
                password = Md5Util.encodeByMd5(password);
            } catch (Exception e) {
            }
            if(user.getPassword().equals(password)){
                if(user.getStatus().equals(Constant.ACTIVED)){
                    return user;
                }else{
                    throw new UserActiveError("该用户尚未激活,请激活后使用");
                }
            }else{
                throw new UserPasswordError("密码错误");
            }
        }else{
            throw new UserUsernameError("用户名错误");
        }
    }


}
