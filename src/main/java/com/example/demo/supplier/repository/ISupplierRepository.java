package com.example.demo.supplier.repository;

import com.example.demo.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findSupplierByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);

}
