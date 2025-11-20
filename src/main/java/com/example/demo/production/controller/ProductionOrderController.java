package com.example.demo.production.controller;


import java.util.List;

import org.springframework.http.MediaType;
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

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductionOrderDTO;
import com.example.demo.production.DTO.response.productionOrder.ProductionOrderDTOResponse;
import com.example.demo.production.entity.enume.ProductionOrderStatus;
import com.example.demo.production.service.IProductionOrderService;
import com.example.demo.utils.Validation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "production-order crud", description = "manage production order")
@RequestMapping("/api/production-order")
public class ProductionOrderController {
    private final IProductionOrderService productionOrderService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CHEF_PRODUCTION')")
    public ResponseEntity<?> createProductionOrder(@Valid @RequestBody ProductionOrderDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(productionOrderService.createProductionOrder(dto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('CHEF_PRODUCTION')")
    public ResponseEntity<?> updateProductionOrder(@Valid @RequestBody ProductionOrderDTO dto, @PathVariable("id") Long id, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(productionOrderService.updateProductionOrder(dto, id));
    }

    @PostMapping("/cancel/{id}")
    @PreAuthorize("hasRole('CHEF_PRODUCTION')")
    public ResponseEntity<ResponseMessage> cancelProductoinOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(productionOrderService.cancelProductionOrder(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('SUPERVISEUR_PRODUCTION')")
    public ResponseEntity<List<ProductionOrderDTOResponse>> getAllProductionOrders(){
        return ResponseEntity.ok(productionOrderService.getAllProductionOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISEUR_PRODUCTION')")
    public ResponseEntity<?> getProductionOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productionOrderService.getProductionOrderById(id));
    }

    @GetMapping("/by-status/{status}")
    @PreAuthorize("hasRole('SUPERVISEUR_PRODUCTION')")
    public ResponseEntity<List<ProductionOrderDTOResponse>> getProductionOrdersByStatus(@PathVariable("status")ProductionOrderStatus status){
        return ResponseEntity.ok(productionOrderService.getProductionOrdersByStatus(status));

    }

    @GetMapping("/estimatinoTime/{id}")
    @PreAuthorize("hasRole('PLANIFICATEUR')")
    public ResponseEntity<?> calculateProductionTimeEstimation(@PathVariable("id") Long id){
        return ResponseEntity.ok("production estimating time in hours is: " + productionOrderService.calculateProductionTimeEstimation(id));
    }
}