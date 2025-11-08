package com.example.demo.production.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductionOrderDTO;
import com.example.demo.production.DTO.response.productionOrder.ProductionOrderDTOResponse;
import com.example.demo.production.entity.enume.ProductionOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface IProductionOrderService {
    ProductionOrderDTOResponse createProductionOrder(ProductionOrderDTO dto);
    ProductionOrderDTOResponse updateProductionOrder(ProductionOrderDTO dto, Long id);
    ResponseMessage cancelProductionOrder(Long id);

    List<ProductionOrderDTOResponse> getAllProductionOrders();
    List<ProductionOrderDTOResponse> getProductionOrdersByStatus(ProductionOrderStatus status);
    ProductionOrderDTOResponse getProductionOrderById(Long id);
    long calculateProductionTimeEstimation(Long id);
}
