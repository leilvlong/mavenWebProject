package com.maven.dao;

import com.maven.domain.Customer;
import com.maven.utils.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerDao {
    JdbcTemplate jt = new JdbcTemplate(JdbcUtil.getDataSource());

    public boolean addCustmort(Customer customer){
        String sql = "insert into customer values(null,?,?,?,?,?,?)";
        int update = 0;
        try{
            update = jt.update(sql,
                    customer.getName(),
                    customer.getSource(),
                    customer.getIndustry(),
                    customer.getLevel(),
                    customer.getAddress(),
                    customer.getMobile()
            );
        }catch (Exception e){
        }
        boolean flag = false;
        if(update == 1){
            flag = true;
        }
        return flag;
    }

}
