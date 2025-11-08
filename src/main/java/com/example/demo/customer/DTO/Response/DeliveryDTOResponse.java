package com.example.demo.customer.DTO.Response;

import com.example.demo.customer.entity.enume.DeliveryStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DeliveryDTOResponse(
         String vehicle,
         String driver,
         DeliveryStatus deliveryStatus,
         LocalDateTime deliveryDate,
         Double cost
) {}