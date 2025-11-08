package com.example.demo.production.repository;

import com.example.demo.production.entity.BillOfMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillOfMaterialRepository extends JpaRepository<BillOfMaterial, Long> {
}
