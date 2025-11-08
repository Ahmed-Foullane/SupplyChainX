package com.example.demo.production.repository;

import com.example.demo.production.entity.ProductionOrder;
import com.example.demo.production.entity.enume.ProductionOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {
    List<ProductionOrder> findProductionOrderByProductionOrderStatus(ProductionOrderStatus status);
}
