package com.example.demo.supplier.repository;
import com.example.demo.supplier.entity.SupplyOrder;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISupplierOrderRepository extends JpaRepository<SupplyOrder, Long> {
    List<SupplyOrder> findSupplyOrderByStatus(SupplyOrderStatus status);
}