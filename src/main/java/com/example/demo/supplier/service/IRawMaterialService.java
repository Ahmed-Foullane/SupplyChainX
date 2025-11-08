package com.example.demo.supplier.service;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.RawMaterialDTO;
import com.example.demo.supplier.DTO.response.rawMaterial.RawMaterialDTOResponse;

import java.util.List;

public interface IRawMaterialService {
    RawMaterialDTOResponse create(RawMaterialDTO dto);
    RawMaterialDTOResponse update(RawMaterialDTO dto, Long id);
    List<RawMaterialDTOResponse> getAll();
    RawMaterialDTOResponse getById(Long id);
    ResponseMessage delete(Long id);
    List<RawMaterialDTOResponse> getByLessThenOrEqualStock(Integer stock);
    List<RawMaterialDTOResponse> getByGreaterThenOrEqualStock(Integer stock);
}
