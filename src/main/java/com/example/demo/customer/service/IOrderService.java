package com.example.demo.customer.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.customer.DTO.OrderDTO;
import com.example.demo.customer.DTO.Response.OrderDTOResponse;
import com.example.demo.customer.entity.enume.OrderStatus;

import java.util.List;

public interface IOrderService {
    OrderDTOResponse createOrder(OrderDTO dto);
    OrderDTOResponse updateOrder(OrderDTO dto, Long id);
    OrderDTOResponse getOrderById(Long id);
    List<OrderDTOResponse> getAllOrders();
    ResponseMessage deleteOrder(Long id);
    List<OrderDTOResponse> getOrdersByStatus(OrderStatus status);

}
