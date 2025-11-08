package com.example.demo.customer.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderDTO (
        @NotNull(message = "the customer id is required")
        Long customerId,
        @NotNull(message = "the product id is required")
        Long productId,
        @NotNull(message = "the quantity is required")
        Integer quantity
){}
