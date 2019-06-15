package com.ssmce.service;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.Product;

import java.util.List;

public interface ProductService {

    public PageInfo findAll(Integer pageNum, Integer pageSize);

    void addProduct(Product product);

    Product productFindById(Integer id);

    void productUpdate(Product product);

    void productDelete(Integer id);

}
