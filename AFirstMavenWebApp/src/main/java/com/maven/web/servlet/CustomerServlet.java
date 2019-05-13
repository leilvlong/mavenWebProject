package com.maven.web.servlet;

import com.maven.domain.Customer;
import com.maven.service.CustomerService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

@WebServlet("/SaveCustomer")
public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer();
        CustomerService customerService = new CustomerService();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(customer,parameterMap);
            boolean b = customerService.addCustomer(customer);
            if (b){
                response.getWriter().write("添加成功");
            }else {
                response.getWriter().write("添加失败");
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

    }
}
