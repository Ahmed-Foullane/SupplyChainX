package com.example.demo.customer.controller;

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
import com.example.demo.customer.DTO.CustomerDTO;
import com.example.demo.customer.service.ICustomerService;
import com.example.demo.utils.Validation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "customer crud" , description = "manage customers")
@RequestMapping("/api/customer")
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> updateCustomer(@PathVariable("id" ) Long id, @RequestBody CustomerDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(customerService.updateCustomer(dto, id));
    };

    @GetMapping("/all")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> getCustomerByid(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasRole('GESTIONNAIRE_COMMERCIAL')")
    public ResponseEntity<?> searchCustomerByname(@PathVariable("name") String name){
        return ResponseEntity.ok(customerService.searchCustomerByName(name));
    }
}
