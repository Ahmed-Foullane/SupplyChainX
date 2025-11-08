package com.example.demo.production.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProductionOrderDTO (
        @NotNull(message = "the product is required")
        Long productId,
        @NotNull(message = "the quantity is required")
        Integer quantity,
        @NotNull(message = "the start date is required")
        LocalDateTime startDate

){}