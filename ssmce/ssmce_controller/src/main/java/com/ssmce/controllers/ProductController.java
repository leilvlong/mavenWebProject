package com.ssmce.controllers;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.Product;
import com.ssmce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public String findAll(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(name="pageSize",required = false,defaultValue = "2")Integer pageSize,
            Model model){
        PageInfo pageInfo = productService.findAll(pageNum, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "product-list.jsp";
    }

    @PostMapping("/add")
    public String productAdd(Product product){
        productService.addProduct(product);
        return "redirect:/product/all";
    }

    @GetMapping("/byId")
    public String productFindById(Integer id,Model model){
        Product product = productService.productFindById(id);
        model.addAttribute("product",product);
        return "product-update.jsp";
    }

    @PostMapping("/update")
    public String productUpdate(Product product){
        productService.productUpdate(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    public String productDelete(Integer id){
        productService.productDelete(id);
        return "redirect:/product/all";
    }

}
