package com.example.demo.customer.controller;

import com.example.demo.customer.DTO.OrderDTO;
import com.example.demo.customer.DTO.Response.OrderDTOResponse;
import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.customer.service.IOrderService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "order crud", description = "manage orders")
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDTO dto,@PathVariable("id") Long id, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(orderService.updateOrder(dto, id));
    }


    @GetMapping("/all")
    public ResponseEntity<List<OrderDTOResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTOResponse>> getOrdersByStatus(@PathVariable("status") OrderStatus status){
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
}
