package com.example.demo.production.mapper;

import com.example.demo.production.DTO.response.product.BOMDTOResponse;
import com.example.demo.production.entity.BillOfMaterial;
import com.example.demo.supplier.mapper.IRawMaterialMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = IRawMaterialMapper.class)
public interface IBOMMapper {
    BOMDTOResponse toDtoResponse(BillOfMaterial billOfMaterial);
}