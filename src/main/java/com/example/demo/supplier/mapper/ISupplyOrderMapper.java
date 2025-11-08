package com.example.demo.supplier.mapper;

import com.example.demo.supplier.DTO.SupplyOrderDTO;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.SupplyOrder;
import org.mapstruct.Mapper;
import com.example.demo.supplier.DTO.response.SupplierOrder.SupplierOrderDTOResponse;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ISupplierMapper.class, IRawMaterialSupplyOrderMapper.class })
public interface ISupplyOrderMapper {

//    @Mapping(source = "rawMaterialSupplyOrders", target = "items")
    SupplierOrderDTOResponse toResponse(SupplyOrder supplyOrder);
}
