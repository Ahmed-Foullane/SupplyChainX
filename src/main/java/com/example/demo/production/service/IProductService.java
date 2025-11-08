package com.example.demo.production.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductDTO;
import com.example.demo.production.DTO.response.product.ProductDTOResponse;

import java.util.List;

public interface IProductService {
    ProductDTOResponse createProduct(ProductDTO productDTO);
    ProductDTOResponse getProductById(Long id);
    List<ProductDTOResponse> getAllProducts();
    ResponseMessage deleteProduct(Long id);
    ProductDTOResponse searchProductByName(String name);
    ProductDTOResponse updateProduct(ProductDTO productDTO, Long id);
}
