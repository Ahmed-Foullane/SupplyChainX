package com.example.demo.supplier.DTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public record SupplyOrderDTO(

        @NotNull(message = "Supplier ID cannot be null")
        Long supplierId,

        @NotNull(message = "Order date cannot be null")
        LocalDateTime orderDate,

        @NotEmpty(message = "Order must have at least one item")
        Map<Long, Integer> items
){}
