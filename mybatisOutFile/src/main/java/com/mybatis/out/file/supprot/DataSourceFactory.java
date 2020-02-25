package com.mybatis.out.file.supprot;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceFactory{


    private static Properties config;
    private static String USERNAME;
    private static String PASSWORD;
    private static String URL;
    private static String DRIVER;
    static {
        InputStream resourceAsStream = DataSourceFactory.class.getClassLoader().getResourceAsStream("supprot-confif.properties");
        config = new Properties();
        try {
            config.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String init(){
            USERNAME = (String)config.getProperty("datasource.username");
            PASSWORD =(String)config.getProperty("datasource.password");
            URL = (String)config.getProperty("datasource.url");
            DRIVER = (String)config.getProperty("datasource.driver");
            return config.getProperty("out.file.path");
    }


    public final static String OUT_FILE_PATH = init();




    public static DataSource getDataSource() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("username", USERNAME);
        map.put("password", PASSWORD);
        map.put("url", URL);
        map.put("driverClassName",DRIVER);
        return DruidDataSourceFactory.createDataSource(map);

    }

}
