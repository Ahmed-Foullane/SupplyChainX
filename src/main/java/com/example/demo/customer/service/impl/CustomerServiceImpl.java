package com.example.demo.customer.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.customer.DTO.CustomerDTO;
import com.example.demo.customer.DTO.Response.CustomerDTOResponse;
import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.entity.Order;
import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.customer.mapper.ICustomerMapper;
import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final ICustomerMapper customerMapper;
    @Override
    public CustomerDTOResponse createCustomer(CustomerDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        return customerMapper.toDtoResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerDTOResponse updateCustomer(CustomerDTO dto, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("the customer id not found"));
        customer.setAddress(dto.address());
        customer.setName(dto.name());
        customer.setCity(dto.city());
        return customerMapper.toDtoResponse(customerRepository.save(customer));
    }

    @Override
    public ResponseMessage deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("the customer id not found"));
        List<Order> orders = customer.getOrders();
        for (Order o: orders){
            if (o.getStatus().equals(OrderStatus.EN_PREPARATION) || o.getStatus().equals(OrderStatus.EN_ROUTE) ){
                return new ResponseMessage("you can't delet this customer barbecue it has active orders");
            }
        }
        customerRepository.delete(customer);
        return new ResponseMessage("customer deleted successfully");

    }

    @Override
    public List<CustomerDTOResponse> getAllCustomers() {
         List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toDtoResponse).toList();
    }

    @Override
    public CustomerDTOResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("the customer id not found"));
        return customerMapper.toDtoResponse(customer);
    }

    @Override
    public CustomerDTOResponse searchCustomerByName(String name) {
        Customer customer = customerRepository.findCustomerByNameContainingIgnoreCase(name);
        return customerMapper.toDtoResponse(customer);
    }
}
