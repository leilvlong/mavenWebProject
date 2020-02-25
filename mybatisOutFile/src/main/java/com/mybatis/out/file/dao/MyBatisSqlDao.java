package com.mybatis.out.file.dao;

import com.mybatis.out.file.domain.MybatisDomain;
import java.util.List;


public interface MyBatisSqlDao {

    List<MybatisDomain> getSql(String tableName);
}
