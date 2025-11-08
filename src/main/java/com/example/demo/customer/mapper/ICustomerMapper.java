package com.example.demo.customer.mapper;


import com.example.demo.customer.DTO.CustomerDTO;
import com.example.demo.customer.DTO.Response.CustomerDTOResponse;
import com.example.demo.customer.DTO.Response.CustomerDTOResponseSanOrder;
import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = IOrderMapper.class)
public interface ICustomerMapper {
    CustomerDTOResponse toDtoResponse(Customer customer);
    Customer toEntity(CustomerDTO dto);
    CustomerDTOResponseSanOrder toDtoResponseSanOrder(Order order);
}