package com.example.demo.production.mapper;

import com.example.demo.production.DTO.ProductionOrderDTO;
import com.example.demo.production.DTO.response.productionOrder.ProductionOrderDTOResponse;
import com.example.demo.production.entity.ProductionOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductionOrderMapper {
    ProductionOrder toEntity(ProductionOrderDTO dto);
    ProductionOrderDTOResponse toDtoResponse(ProductionOrder productionOrder);
}
