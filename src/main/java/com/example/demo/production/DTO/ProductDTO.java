package com.example.demo.production.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record ProductDTO(
        @NotEmpty(message = "the name is required")
        String name,
        @NotNull(message = "the date is required")
        Integer productionTime,
        @NotNull(message = "the cost is required")
        Double cost,
        @NotNull(message = "the stock is required")
        Integer stock,
        Map<Long, Integer> rawMaterials
) {
}
