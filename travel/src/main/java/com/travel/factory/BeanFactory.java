package com.travel.factory;

import com.travel.dao.ICategoryDao;
import com.travel.dao.IFavoriteDao;
import com.travel.dao.IRouteDao;
import com.travel.service.ICategoryService;
import com.travel.service.IFavotiteService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BeanFactory {
    private static Properties classNames;
    static {
        InputStream rs = BeanFactory.class.getClassLoader().getResourceAsStream("servlet-relyOn-config.properties");
        try {
            classNames = new Properties();
            classNames.load(rs);
        } catch (IOException e) {
            throw new RuntimeException("properties config error");
        }finally {
            try {
                rs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getBean(String classRelyOn){
        String className = classNames.getProperty(classRelyOn);
        Object classes = null;
        try {
            classes = Class.forName(className).newInstance();
        }catch (Exception e) {
            throw new RuntimeException("classRelyOn error or servlet-relyOn-config.properties No classRelyOn");
        }

        return classes;
    }

    public static void main(String[] args) {
        IFavotiteService fs = (IFavotiteService) BeanFactory.getBean("favoriteServiceImpl");
        IFavoriteDao fd = (IFavoriteDao) BeanFactory.getBean("favoriteDaoImpl");
        System.out.println(fs);
        System.out.println(fd);
    }
}
