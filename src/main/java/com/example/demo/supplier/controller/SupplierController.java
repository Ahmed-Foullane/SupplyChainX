package com.example.demo.supplier.controller;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.mapper.ISupplierMapper;
import com.example.demo.supplier.service.ISupplierService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "supplier crud", description = "manage suppliers")
@RequestMapping("/api/supplier")
public class SupplierController {
    private final ISupplierService supplierService;
    private final ISupplierMapper supplierMapper;
    private final RestTemplateBuilder restTemplateBuilder;

    @GetMapping
    @PreAuthorize("hasRole('SUPERVISEUR_LOGISTIQUE')")
    public ResponseEntity<List<SupplierDTOResponse>> getAll(){
        List<SupplierDTOResponse> suppliers = supplierService.getAllSuplliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('SUPERVISEUR_LOGISTIQUE')")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        SupplierDTOResponse supplierDTOResponse = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplierDTOResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('GESTIONNAIRE_APPROVISIONNEMENT')")
    public ResponseEntity<?> create(@Valid @RequestBody SupplierDTO supplierDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(supplierService.createSupplier(supplierDTO));
    }

    @PutMapping("/update/id/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE_APPROVISIONNEMENT')")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Long id, @RequestBody SupplierDTO supplierDTO, BindingResult result){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        SupplierDTO supplier = supplierService.updateSupplier(supplierDTO, id);
        return  ResponseEntity.ok(supplier);
    }

    @DeleteMapping("/delete/id/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE_APPROVISIONNEMENT')")
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") Long id){
        ResponseMessage message = supplierService.deleteSupplier(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/search/{firstName}/{lastName}")
    @PreAuthorize("hasRole('RESPONSABLE_ACHATS')")
    public ResponseEntity<?> searchSupplierByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        SupplierDTOResponse supplier = supplierService.searchSupplierByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(supplier);
    }
}
