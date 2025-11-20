package com.example.demo.supplier.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplyOrderDTO;
import com.example.demo.supplier.DTO.response.SupplierOrder.SupplierOrderDTOResponse;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.service.ISupplierOrderService;
import com.example.demo.utils.Validation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Tag(name = "supplier order crud", description = "manage suppliers orders")
@RequestMapping("/api/supplier-order")
public class SupplierOrderController {
    private final ISupplierOrderService supplierOrderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('RESPONSABLE_ACHATS')")
    public ResponseEntity<?> createSupplierOrder(@Valid @RequestBody SupplyOrderDTO supplyOrderDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(supplierOrderService.createSupplierOrder(supplyOrderDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPERVISEUR_LOGISTIQUE')")
    public ResponseEntity<List<SupplierOrderDTOResponse>> getAllSupplierOrders(){
        return ResponseEntity.ok(supplierOrderService.getAllSuplliersOrders());
    }
     @GetMapping("/id/{id}")
     @PreAuthorize("hasRole('SUPERVISEUR_LOGISTIQUE')")
    public ResponseEntity<?> getSupplierOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(supplierOrderService.getSupplierOrderById(id));
    }

    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('RESPONSABLE_ACHATS')")
    public ResponseEntity<ResponseMessage> deleteSupplierOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(supplierOrderService.deleteSupplierOrder(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('RESPONSABLE_ACHATS')")
    public ResponseEntity<?> updateSupplierOrder(@Valid @PathVariable("id") Long id, @RequestBody SupplyOrderDTO supplyOrderDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        SupplierOrderDTOResponse supplyOrder = supplierOrderService.updateSupplierOrder(supplyOrderDTO, id);
        return ResponseEntity.ok(supplyOrder);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('SUPERVISEUR_LOGISTIQUE')")
    public ResponseEntity<List<SupplierOrderDTOResponse>> getSuppliersOrdersByStatus(@PathVariable("status") SupplyOrderStatus status){
        List<SupplierOrderDTOResponse> rawMaterialDTOResponses = supplierOrderService.getSupplierOrdersByStatus(status);
        return ResponseEntity.ok(rawMaterialDTOResponses);
    }

 }
