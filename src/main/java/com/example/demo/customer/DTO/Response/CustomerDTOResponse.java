package com.example.demo.customer.DTO.Response;

import java.util.List;

public record CustomerDTOResponse(
        String name,
        String address,
        String city,
        List<OrderDTOResponseSanCustomer> orders
) {}