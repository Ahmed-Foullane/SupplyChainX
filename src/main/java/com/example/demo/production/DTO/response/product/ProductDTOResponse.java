package com.example.demo.production.DTO.response.product;

import org.mapstruct.Mapper;

import java.util.List;

public record ProductDTOResponse (
        String name,
        Integer productionTime,
        Double cost,
        List<BOMDTOResponse> billOfMaterials
){}
