package com.example.demo.supplier.mapper;

import com.example.demo.supplier.DTO.RawMaterialDTO;
import com.example.demo.supplier.DTO.response.rawMaterial.RawMaterialDTOResponse;
import com.example.demo.supplier.entity.RawMaterial;
import org.mapstruct.Mapper;
import com.example.demo.supplier.DTO.response.supplier.RawMaterialDTOResponseWithoutList;
@Mapper(componentModel = "spring", uses = ISupplierMapper.class)
public interface IRawMaterialMapper {
    RawMaterialDTO toDto(RawMaterial rawMaterial);
    RawMaterial toEntity(RawMaterialDTO rawMaterialDTO);
    RawMaterialDTOResponse toResponseDto(RawMaterial rawMaterial);
    RawMaterialDTOResponseWithoutList toDtoWithoutList(RawMaterial rawMaterial);
}
