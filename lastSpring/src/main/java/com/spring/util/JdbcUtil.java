package com.spring.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
    private static DataSource dataSource;
    static {
        Properties druidConfig = new Properties();
        InputStream ril = JdbcUtil.class.getClassLoader().getResourceAsStream("druid-config.properties");
        try {
            druidConfig.load(ril);
            dataSource = DruidDataSourceFactory.createDataSource(druidConfig);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ril.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void main(String[] args) {
        System.out.println(getDataSource());
    }


}
