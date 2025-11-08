package com.example.demo.production.mapper;

import com.example.demo.production.DTO.ProductDTO;
import com.example.demo.production.DTO.response.product.ProductDTOResponse;
import com.example.demo.production.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = IBOMMapper.class )
public interface IProductMapper {
    Product toEntity(ProductDTO dto);
    ProductDTOResponse toDtoResponse(Product product);
}
