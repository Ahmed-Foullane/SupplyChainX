package com.example.demo.customer.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.customer.DTO.OrderDTO;
import com.example.demo.customer.DTO.Response.OrderDTOResponse;
import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.entity.Delivery;
import com.example.demo.customer.entity.Order;
import com.example.demo.customer.entity.enume.DeliveryStatus;
import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.customer.mapper.IOrderMapper;
import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.customer.repository.DeliveryRepository;
import com.example.demo.customer.repository.OrderRepository;
import com.example.demo.customer.service.IOrderService;
import com.example.demo.production.entity.Product;
import com.example.demo.production.repository.IProductRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final IOrderMapper orderMapper;
    private final IProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public OrderDTOResponse createOrder(OrderDTO dto) {
        Order order = orderMapper.toEntity(dto);
        Product product = productRepository.findById(dto.productId()).orElseThrow(()-> new RuntimeException("the product id not found: " + dto.productId()));
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("the customer id not found: " + dto.customerId()));
        if (product.getStock() < dto.quantity()) {
            throw new RuntimeException("the stock is not enough");
        }
        product.setStock(product.getStock() - dto.quantity());
        productRepository.save(product);
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setVehicle("car");
        delivery.setDriver("driver");
        delivery.setCost(product.getCost()*dto.quantity());
        delivery.setDeliveryStatus(DeliveryStatus.EN_COURS);
        delivery.setDeliveryDate(LocalDateTime.now().plusDays(5));
        deliveryRepository.save(delivery);
        order.setCustomer(customer);
        order.setProduct(product);
        order.setDelivery(delivery);
        order.setStatus(OrderStatus.EN_PREPARATION);
        return orderMapper.toDtoResponse(orderRepository.save(order));
    }

    @Override
    public OrderDTOResponse updateOrder(OrderDTO dto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("the order id not found: " + id));
        order.getDelivery().setDeliveryDate(LocalDateTime.now().plusDays(5));
        Product previousProduct = order.getProduct();
        Product product = productRepository.findById(dto.productId()).orElseThrow(()-> new RuntimeException("the product id not found: " + dto.productId()));
        Customer customer = customerRepository.findById(dto.customerId()).orElseThrow(() -> new RuntimeException("the customer id not found: " + dto.customerId()));
        Delivery delivery = order.getDelivery();
        delivery.setCost(product.getCost()*dto.quantity());

        previousProduct.setStock(previousProduct.getStock() + order.getQuantity());
        if (product.getStock() < dto.quantity()) {
            throw new RuntimeException("the stock is not enough");
        }
        product.setStock(product.getStock() - dto.quantity());
        productRepository.save(previousProduct);
        if (!previousProduct.getId().equals(product.getId())){
          productRepository.save(product);
        }

        order.setQuantity(dto.quantity());
        order.setCustomer(customer);
        order.setProduct(product);
        return orderMapper.toDtoResponse(orderRepository.save(order));
    }

    @Override
    public OrderDTOResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("the order id not found: " + id));
        return orderMapper.toDtoResponse(order);
    }

    @Override
    public List<OrderDTOResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTOResponse> orderDTOResponses = orders.stream().map(orderMapper::toDtoResponse).toList();
        return orderDTOResponses;
    }

    @Override
    public ResponseMessage deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("the ordr id not found: " + id));
        if (!order.getStatus().equals(OrderStatus.LIVREE)){
            return new ResponseMessage("you can't delete this order");
        }
        orderRepository.delete(order);
        return new ResponseMessage("the order deleted successfully");
    }

    @Override
    public List<OrderDTOResponse> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findOrdersByStatus(status);
        return orders.stream().map(orderMapper::toDtoResponse).toList();
    }
}
