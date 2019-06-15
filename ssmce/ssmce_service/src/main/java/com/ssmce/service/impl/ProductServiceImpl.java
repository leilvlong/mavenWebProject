package com.ssmce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssmce.dao.ProductDao;
import com.ssmce.domain.Product;
import com.ssmce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public PageInfo findAll(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Product> list = productDao.findAll();
        return new PageInfo<Product>(list);
    }

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public Product productFindById(Integer id) {
        return productDao.productFindById(id);
    }

    public void productUpdate(Product product) {
        productDao.productUpdate(product);
    }

    public void productDelete(Integer id) {
        productDao.productDelete(id);
    }
}
