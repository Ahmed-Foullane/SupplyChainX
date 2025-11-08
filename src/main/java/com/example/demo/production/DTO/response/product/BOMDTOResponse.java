package com.example.demo.production.DTO.response.product;

import com.example.demo.supplier.DTO.response.rawMaterial.RawMaterialDTOResponse;
import com.example.demo.supplier.DTO.response.supplier.RawMaterialDTOResponseWithoutList;

public record BOMDTOResponse (
    RawMaterialDTOResponseWithoutList rawMaterial,
    Integer quantity
)


{}
