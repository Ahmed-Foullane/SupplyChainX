package com.example.demo.customer.repository;

import com.example.demo.customer.entity.Order;
import com.example.demo.customer.entity.enume.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findOrdersByStatus(OrderStatus status);
}
