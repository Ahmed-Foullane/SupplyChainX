package com.example.demo.supplier.DTO.response.SupplierOrder;

import com.example.demo.supplier.DTO.response.supplier.RawMaterialDTOResponseWithoutList;

public record RawMaterialSupplyOrderDTOResponse (
        Long id,
        Integer quantity,
        RawMaterialDTOResponseWithoutList rawMaterial
){}
