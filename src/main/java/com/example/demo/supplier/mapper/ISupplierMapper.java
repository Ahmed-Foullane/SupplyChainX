package com.example.demo.supplier.mapper;

import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.response.rawMaterial.SupplierDTOResponseWithoutList;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISupplierMapper {
    SupplierDTO toDTO(Supplier supplier);
    Supplier toEntity(SupplierDTO supplierDTO);
    SupplierDTOResponse toResponseDTO(Supplier supplier);
    SupplierDTOResponseWithoutList toResponseDTOWithoutList(Supplier supplier);


}