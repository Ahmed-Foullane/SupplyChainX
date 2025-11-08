package com.example.demo.production.repository;

import com.example.demo.production.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findProductByNameContainingIgnoreCase(String name);
}
