package com.example.demo.supplier.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.Supplier;

import java.util.List;

public interface ISupplierService {
    List<SupplierDTOResponse> getAllSuplliers();
    SupplierDTOResponse getSupplierById(Long id);
    Supplier createSupplier(SupplierDTO supplier);
    SupplierDTO updateSupplier(SupplierDTO supplier, Long id);
    ResponseMessage deleteSupplier(Long id);
    SupplierDTOResponse searchSupplierByFirstNameAndLastName(String firstName, String lastName);
}
