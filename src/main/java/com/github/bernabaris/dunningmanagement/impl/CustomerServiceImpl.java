package com.github.bernabaris.dunningmanagement.impl;

import com.github.bernabaris.dunningmanagement.entity.Customer;
import com.github.bernabaris.dunningmanagement.repository.CustomerRepository;
import com.github.bernabaris.dunningmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }
}
