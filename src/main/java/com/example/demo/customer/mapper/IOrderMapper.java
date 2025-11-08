package com.example.demo.customer.mapper;


import com.example.demo.customer.DTO.OrderDTO;
import com.example.demo.customer.DTO.Response.OrderDTOResponse;
import com.example.demo.customer.DTO.Response.OrderDTOResponseSanCustomer;
import com.example.demo.customer.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    OrderDTOResponse toDtoResponse(Order order);
    Order toEntity(OrderDTO dto);
    OrderDTOResponseSanCustomer toDtoResponseSanCustomer(Order order);
}
