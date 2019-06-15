package com.ssmce.service;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.Orders;
import com.ssmce.domain.Product;

import java.util.List;

public interface OrdersService {
    PageInfo ordersAll(Integer pageNum, Integer pageSize);

    int ordersAdd(Orders orders);

    List<Product> productfindAll();


}
