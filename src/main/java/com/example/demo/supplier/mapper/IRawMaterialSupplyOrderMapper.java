    package com.example.demo.supplier.mapper;

    import com.example.demo.supplier.DTO.response.SupplierOrder.RawMaterialSupplyOrderDTOResponse;
    import com.example.demo.supplier.entity.RawMaterialSupplyOrder;
    import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring", uses = IRawMaterialMapper.class)
    public interface IRawMaterialSupplyOrderMapper {
        RawMaterialSupplyOrderDTOResponse toDtoResponse(RawMaterialSupplyOrder rawMaterialSupplyOrder);
    }
