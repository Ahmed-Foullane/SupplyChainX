package com.example.demo.supplier.repository;

import com.example.demo.supplier.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRawMaterialRepository  extends JpaRepository<RawMaterial, Long> {
    List<RawMaterial> findRawMaterialByStockLessThanEqual(Integer stock);
    List<RawMaterial> findRawMaterialByStockGreaterThanEqual(Integer stock);
}
