package com.example.demo.supplier.controller;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.SupplyOrderDTO;
import com.example.demo.supplier.DTO.response.SupplierOrder.SupplierOrderDTOResponse;
import com.example.demo.supplier.DTO.response.rawMaterial.RawMaterialDTOResponse;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.SupplyOrder;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.service.ISupplierOrderService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "supplier order crud", description = "manage suppliers orders")
@RequestMapping("/api/supplier-order")
public class SupplierOrderController {
    private final ISupplierOrderService supplierOrderService;

    @PostMapping("/create")
    public ResponseEntity<?> createSupplierOrder(@Valid @RequestBody SupplyOrderDTO supplyOrderDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(supplierOrderService.createSupplierOrder(supplyOrderDTO));
    }

    @GetMapping
    public ResponseEntity<List<SupplierOrderDTOResponse>> getAllSupplierOrders(){
        return ResponseEntity.ok(supplierOrderService.getAllSuplliersOrders());
    }
     @GetMapping("/id/{id}")
    public ResponseEntity<?> getSupplierOrderById(@PathVariable("id") Long id){
        return ResponseEntity.ok(supplierOrderService.getSupplierOrderById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ResponseMessage> deleteSupplierOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(supplierOrderService.deleteSupplierOrder(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSupplierOrder(@Valid @PathVariable("id") Long id, @RequestBody SupplyOrderDTO supplyOrderDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        SupplierOrderDTOResponse supplyOrder = supplierOrderService.updateSupplierOrder(supplyOrderDTO, id);
        return ResponseEntity.ok(supplyOrder);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SupplierOrderDTOResponse>> getSuppliersOrdersByStatus(@PathVariable("status") SupplyOrderStatus status){
        List<SupplierOrderDTOResponse> rawMaterialDTOResponses = supplierOrderService.getSupplierOrdersByStatus(status);
        return ResponseEntity.ok(rawMaterialDTOResponses);
    }

 }
