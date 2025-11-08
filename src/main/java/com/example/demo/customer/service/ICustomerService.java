package com.example.demo.customer.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.customer.DTO.CustomerDTO;
import com.example.demo.customer.DTO.Response.CustomerDTOResponse;

import java.util.List;

public interface ICustomerService {
    CustomerDTOResponse createCustomer(CustomerDTO dto);
    CustomerDTOResponse updateCustomer(CustomerDTO dto, Long id);
    ResponseMessage deleteCustomer(Long id);
    List<CustomerDTOResponse> getAllCustomers();
    CustomerDTOResponse getCustomerById(Long id);
    CustomerDTOResponse searchCustomerByName(String name);
}
