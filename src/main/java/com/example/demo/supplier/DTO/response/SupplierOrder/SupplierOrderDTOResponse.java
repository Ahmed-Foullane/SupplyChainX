package com.example.demo.supplier.DTO.response.SupplierOrder;

import com.example.demo.supplier.DTO.response.rawMaterial.SupplierDTOResponseWithoutList;

import com.example.demo.supplier.entity.enume.SupplyOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SupplierOrderDTOResponse (
        Long idOrder,
        SupplierDTOResponseWithoutList supplier,
        LocalDateTime orderDate,
        SupplyOrderStatus status,
        List<RawMaterialSupplyOrderDTOResponse> rawMaterialSupplyOrders
){}