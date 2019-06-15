package com.ssmce.controllers;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.Orders;
import com.ssmce.domain.Product;
import com.ssmce.service.OrdersService;
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
@RequestMapping("/orders")
public class OrdersController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/all")
    public String ordersAll(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(name="pageSize",required = false,defaultValue = "2")Integer pageSize,
            Model model){
        PageInfo pageInfo = ordersService.ordersAll(pageNum, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "order-list.jsp";
    }



    @PostMapping("/addto")
    public String ordersAdd(Orders orders){
        System.out.println(orders);
        ordersService.ordersAdd(orders);
        return "redirect:/orders/all";
    }

    @GetMapping("/addfor")
    public String ordersFor(Model model){
         List<Product> list= ordersService.productfindAll();
         model.addAttribute("pList",list);
         return "order-add.jsp";
    }
}
