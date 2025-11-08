package com.example.demo.customer.DTO.Response;

import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.production.DTO.response.product.ProductDTOResponse;

public record OrderDTOResponse(
        ProductDTOResponse product,
        Integer quantity,
        DeliveryDTOResponse delivery,
        OrderStatus status,
        CustomerDTOResponseSanOrder customer
) {}