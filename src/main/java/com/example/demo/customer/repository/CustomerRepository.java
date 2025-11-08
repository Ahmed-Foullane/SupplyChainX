package com.example.demo.customer.repository;

import com.example.demo.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByNameContainingIgnoreCase(String name);
}
