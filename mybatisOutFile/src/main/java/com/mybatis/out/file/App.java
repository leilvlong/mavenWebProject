package com.mybatis.out.file;





import com.mybatis.out.file.dao.MyBatisSqlDao;
import com.mybatis.out.file.domain.MybatisDomain;
import com.mybatis.out.file.supprot.DataSourceFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class App{
    public static void main(String[] args) throws Exception {

        DataSource dataSource = DataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(MyBatisSqlDao.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        MyBatisSqlDao mapper = sqlSessionFactory.openSession().getMapper(MyBatisSqlDao.class);
        List<MybatisDomain> area = mapper.getSql("area");
        System.out.println(area);

    }
}

