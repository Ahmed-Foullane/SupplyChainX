package com.example.demo.customer.DTO.Response;

import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.production.DTO.response.product.ProductDTOResponse;
import jakarta.persistence.criteria.CriteriaBuilder;

public record OrderDTOResponseSanCustomer(

        ProductDTOResponse product,
        Integer quantity,
        DeliveryDTOResponse delivery,
        OrderStatus status
) {}
