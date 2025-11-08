package com.example.demo.customer.DTO;

import jakarta.validation.constraints.NotEmpty;

public record CustomerDTO (

        @NotEmpty(message = "the name is required")
        String name,
        @NotEmpty(message = "the adress is required")
        String address,
        @NotEmpty(message = "the city is required")
        String city
){}
