package com.ssmce.dao;

import com.ssmce.domain.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductDao {

    @Select("select * from product")
    public List<Product> findAll();

    @Insert("insert into product values(0,#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void addProduct(Product product);

    @Select("select * from product where id=#{id}")
    Product productFindById(Integer id);

    @Update("update product set id=#{id},productNum=#{productNum},productName=#{productName},cityName=#{cityName},departureTime=#{departureTime},productPrice=#{productPrice},productDesc=#{productDesc},productStatus=#{productStatus} where id=#{id}")
    void productUpdate(Product product);

    @Delete("delete from product where id=#{id}")
    void productDelete(Integer id);
}
