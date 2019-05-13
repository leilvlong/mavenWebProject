package com.maven.service;

import com.maven.dao.CustomerDao;
import com.maven.domain.Customer;

public class CustomerService {
    CustomerDao cd = new CustomerDao();

    public boolean addCustomer(Customer customer){
        return cd.addCustmort(customer);
    }
}
