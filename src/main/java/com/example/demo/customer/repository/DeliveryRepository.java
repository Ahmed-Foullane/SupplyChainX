package com.example.demo.customer.repository;

import com.example.demo.customer.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
