package com.github.bernabaris.dunningmanagement.service;

import com.github.bernabaris.dunningmanagement.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    List<Customer> listCustomers();
}
