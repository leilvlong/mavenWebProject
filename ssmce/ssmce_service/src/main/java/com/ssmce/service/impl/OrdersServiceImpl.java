package com.ssmce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssmce.dao.OrdersDao;
import com.ssmce.domain.Orders;
import com.ssmce.domain.Product;
import com.ssmce.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    public PageInfo ordersAll(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Orders> orders = ordersDao.ordersAll();
        return new PageInfo<Orders>(orders);
    }

    public int ordersAdd(Orders orders) {
        return ordersDao.ordersAdd(orders);
    }

    public List<Product> productfindAll() {
        return ordersDao.productfindAll();
    }
}
