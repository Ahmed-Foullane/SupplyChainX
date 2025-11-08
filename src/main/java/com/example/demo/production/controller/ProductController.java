package com.example.demo.production.controller;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductDTO;
import com.example.demo.production.DTO.response.product.ProductDTOResponse;
import com.example.demo.production.service.IProductService;
import com.example.demo.utils.Validation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "product crud", description = "manage products")
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final IProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTOResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchProductByName(@PathVariable("name") String name){
       return ResponseEntity.ok(productService.searchProductByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable("id") Long id, @RequestBody  ProductDTO productDTO, BindingResult result ){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Validation.getValidationErrors(result));
        }
        return ResponseEntity.ok(productService.updateProduct(productDTO, id));
        }
}

