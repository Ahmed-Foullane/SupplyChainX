package com.example.demo.supplier.service.impl;

import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.entity.Supplier;
import com.example.demo.supplier.mapper.ISupplierMapper;
import com.example.demo.supplier.repository.ISupplierRepository;
import com.example.demo.supplier.service.ISupplierService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

//    @Mock
//    private ISupplierRepository supplierRepository;
//
//    @Mock
//    private ISupplierMapper supplierMapper;
//
//    @InjectMocks
//    private SupplierServiceImpl supplierService;
//
//
//    @Test
//    void createSupplier() {
//        SupplierDTO supplierDTO = new SupplierDTO();
//        supplierDTO.setContact("8213738");
//        supplierDTO.setLastName("tima");
//        supplierDTO.setLeadTime(2);
//        supplierDTO.setRating(7.1);
//
//        // Mocked entity returned by the mapper
//        Supplier supplier = new Supplier();
//        supplier.setContact("8213738");
//        supplier.setLastName("tima");
//        supplier.setLeadTime(2);
//        supplier.setRating(7.1);
//
//        Mockito.when(supplierMapper.toEntity(supplierDTO)).thenReturn(supplier);
//        Mockito.when(supplierRepository.save(supplier)).thenReturn(supplier);
//
//        Supplier createdSupplier = supplierService.createSupplier(supplierDTO);
//
//        Assertions.assertEquals(supplier.getRating(), createdSupplier.getRating());
//    }
//
//
//    @Test
//    void updateSupplier() {
//    }
//
//    @Test
//    void deleteSupplier() {
//    }
//
//    @Test
//    void searchSupplierByFirstNameAndLastName() {
//    }
}