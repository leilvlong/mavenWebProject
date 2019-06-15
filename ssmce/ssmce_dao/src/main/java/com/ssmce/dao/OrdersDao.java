package com.ssmce.dao;

import com.ssmce.domain.Orders;
import com.ssmce.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrdersDao {


    List<Orders> ordersAll();


    int ordersAdd(Orders orders);

    List<Product> productfindAll();

}
