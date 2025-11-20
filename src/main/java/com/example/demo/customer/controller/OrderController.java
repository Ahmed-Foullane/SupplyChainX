package com.example.demo.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customer.DTO.OrderDTO;
import com.example.demo.customer.DTO.Response.OrderDTOResponse;
import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.customer.service.IOrderService;
import com.example.demo.utils.Validation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "order crud", description = "manage orders")
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDTO dto,@PathVariable("id") Long id, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(orderService.updateOrder(dto, id));
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('SUPERVISEUR_LIVRAISONS')")
    public ResponseEntity<List<OrderDTOResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISEUR_LIVRAISONS')")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('SUPERVISEUR_LIVRAISONS')")
    public ResponseEntity<List<OrderDTOResponse>> getOrdersByStatus(@PathVariable("status") OrderStatus status){
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
}
