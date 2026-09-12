package com.github.bernabaris.dunningmanagement.repository;

import com.github.bernabaris.dunningmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
