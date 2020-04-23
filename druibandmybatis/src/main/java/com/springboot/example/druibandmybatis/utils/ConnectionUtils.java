package com.springboot.example.druibandmybatis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionUtils {

    private final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Autowired
    DataSource dataSource;

    public Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null){
            return connection;
        }

        threadLocal.set(dataSource.getConnection());
        return threadLocal.get();
    }

    public void removeConnection(){
        threadLocal.remove();
    }


}
