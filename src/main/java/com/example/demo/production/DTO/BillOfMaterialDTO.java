package com.example.demo.production.DTO;

import jakarta.validation.constraints.NotNull;

public record BillOfMaterialDTO(    
        @NotNull(message = "the product is required")
        Long productId,
        @NotNull(message = "the material is required")
        Long materialId,
        @NotNull(message = "the quantity is required")
        Integer quantity

) {}
