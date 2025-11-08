package com.example.demo.customer.controller;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.customer.DTO.CustomerDTO;
import com.example.demo.customer.service.ICustomerService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "customer crud" , description = "manage customers")
@RequestMapping("/api/customer")
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id" ) Long id, @RequestBody CustomerDTO dto, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(customerService.updateCustomer(dto, id));
    };

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerByid(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteCustomer(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchCustomerByname(@PathVariable("name") String name){
        return ResponseEntity.ok(customerService.searchCustomerByName(name));
    }
}
