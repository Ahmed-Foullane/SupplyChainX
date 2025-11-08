package com.example.demo.production.DTO.response.productionOrder;

import com.example.demo.production.DTO.response.product.ProductDTOResponse;
import com.example.demo.production.entity.enume.ProductionOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProductionOrderDTOResponse (
        Integer quantity,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ProductDTOResponse product,
        ProductionOrderStatus productionOrderStatus
){}