package com.example.demo.production.controller;


import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductionOrderDTO;
import com.example.demo.production.DTO.response.productionOrder.ProductionOrderDTOResponse;
import com.example.demo.production.entity.enume.ProductionOrderStatus;
import com.example.demo.production.service.IProductionOrderService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "production-order crud", description = "manage production order")
@RequestMapping("/api/production-order")
public class ProductionOrderController {
    private final IProductionOrderService productionOrderService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProductionOrder(@Valid @RequestBody ProductionOrderDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(productionOrderService.createProductionOrder(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProductionOrder(@Valid @RequestBody ProductionOrderDTO dto, @PathVariable("id") Long id, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(productionOrderService.updateProductionOrder(dto, id));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<ResponseMessage> cancelProductoinOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(productionOrderService.cancelProductionOrder(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductionOrderDTOResponse>> getAllProductionOrders(){
        return ResponseEntity.ok(productionOrderService.getAllProductionOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductionOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productionOrderService.getProductionOrderById(id));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<ProductionOrderDTOResponse>> getProductionOrdersByStatus(@PathVariable("status")ProductionOrderStatus status){
        return ResponseEntity.ok(productionOrderService.getProductionOrdersByStatus(status));

    }

    @GetMapping("/estimatinoTime/{id}")
    public ResponseEntity<?> calculateProductionTimeEstimation(@PathVariable("id") Long id){
        return ResponseEntity.ok("production estimating time in hours is: " + productionOrderService.calculateProductionTimeEstimation(id));
    }
}