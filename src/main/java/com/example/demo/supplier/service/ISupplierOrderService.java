package com.example.demo.supplier.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplyOrderDTO;
import com.example.demo.supplier.DTO.response.SupplierOrder.SupplierOrderDTOResponse;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;

import java.util.List;

public interface ISupplierOrderService {
    List<SupplierOrderDTOResponse> getAllSuplliersOrders();
    SupplierOrderDTOResponse getSupplierOrderById(Long id);
    SupplierOrderDTOResponse createSupplierOrder(SupplyOrderDTO supplyOrderDTO);
    SupplierOrderDTOResponse updateSupplierOrder(SupplyOrderDTO supplyOrderDTO, Long id);
    ResponseMessage deleteSupplierOrder(Long id);
    List<SupplierOrderDTOResponse> getSupplierOrdersByStatus(SupplyOrderStatus status);
}
