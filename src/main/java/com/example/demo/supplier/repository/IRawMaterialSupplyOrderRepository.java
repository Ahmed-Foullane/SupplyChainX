package com.example.demo.supplier.repository;

import com.example.demo.supplier.entity.RawMaterialSupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRawMaterialSupplyOrderRepository extends JpaRepository<RawMaterialSupplyOrder, Long> {
}
