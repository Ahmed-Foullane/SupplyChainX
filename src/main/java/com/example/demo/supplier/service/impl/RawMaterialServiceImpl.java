package com.example.demo.supplier.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.RawMaterialDTO;
import com.example.demo.supplier.DTO.response.rawMaterial.RawMaterialDTOResponse;
import com.example.demo.supplier.entity.RawMaterial;
import com.example.demo.supplier.entity.RawMaterialSupplyOrder;
import com.example.demo.supplier.entity.Supplier;
import com.example.demo.supplier.mapper.IRawMaterialMapper;
import com.example.demo.supplier.mapper.IRawMaterialSupplyOrderMapper;
import com.example.demo.supplier.repository.IRawMaterialRepository;
import com.example.demo.supplier.repository.IRawMaterialSupplyOrderRepository;
import com.example.demo.supplier.repository.ISupplierRepository;
import com.example.demo.supplier.service.IRawMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class RawMaterialServiceImpl implements IRawMaterialService {
    private final IRawMaterialRepository rawMaterialRepository;
    private final IRawMaterialMapper rawMaterialMapper;
    private final ISupplierRepository supplierRepository;
    private final IRawMaterialSupplyOrderRepository rawMaterialSupplyOrderRepository;
        @Override
        public RawMaterialDTOResponse create(RawMaterialDTO dto) {
            RawMaterial rawMaterial = rawMaterialMapper.toEntity(dto);
            if (dto.getSupplierIds() != null && !dto.getSupplierIds().isEmpty()) {
                List<Supplier> suppliers = supplierRepository.findAllById(dto.getSupplierIds());
                rawMaterial.setSuppliers(suppliers);
                for (Supplier supplier : suppliers) {
                    supplier.getRawMaterials().add(rawMaterial);
                }
            }
            RawMaterial rawMaterial1 = rawMaterialRepository.save(rawMaterial);
            RawMaterialDTOResponse rawMaterialDTO = rawMaterialMapper.toResponseDto(rawMaterial1);
            return rawMaterialDTO;
        }

    @Override
    public RawMaterialDTOResponse getById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(()-> new RuntimeException("raw material id not found: " + id));
        RawMaterialDTOResponse rawMaterialDTOResponse = rawMaterialMapper.toResponseDto(rawMaterial);
        return rawMaterialDTOResponse;
    }

    @Override
    public RawMaterialDTOResponse update(RawMaterialDTO dto, Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("raw material id not found: " + id));

        rawMaterial.setName(dto.getName());
        rawMaterial.setStock(dto.getStock());
        rawMaterial.setUnit(dto.getUnit());
        rawMaterial.setStockMin(dto.getStockMin());

        if (dto.getSupplierIds() != null) {
            for (Supplier oldSupplier : rawMaterial.getSuppliers()) {
                oldSupplier.getRawMaterials().remove(rawMaterial);
            }
            List<Supplier> newSuppliers = supplierRepository.findAllById(dto.getSupplierIds());
            rawMaterial.setSuppliers(newSuppliers);
            for (Supplier newSupplier : newSuppliers) {
                newSupplier.getRawMaterials().add(rawMaterial);
            }
        }
        RawMaterial rawMaterial1 = rawMaterialRepository.save(rawMaterial);
        RawMaterialDTOResponse rawMaterialDTO = rawMaterialMapper.toResponseDto(rawMaterial1);
        return rawMaterialDTO;
    }
    @Override
    public List<RawMaterialDTOResponse> getAll() {
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        List<RawMaterialDTOResponse> rawMaterialDTOResponses = rawMaterials.stream().map(rawMaterialMapper::toResponseDto).toList();
        return rawMaterialDTOResponses;
    }


    @Override
    public ResponseMessage delete(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("raw material id not found: " + id));

        List<RawMaterialSupplyOrder> rawMaterialSupplyOrders = rawMaterialSupplyOrderRepository.findAll();
        for (RawMaterialSupplyOrder r: rawMaterialSupplyOrders){
            if (r.getRawMaterial().equals(rawMaterial)){
               return new ResponseMessage("you can't delete this raw material because it belongs to an order");
            }
        }
        if (rawMaterial.getSuppliers() != null && !rawMaterial.getSuppliers().isEmpty()) {
            return new ResponseMessage("You cannot delete this raw material because it is linked suppliers");
        }
        rawMaterialRepository.delete(rawMaterial);
        return new ResponseMessage("The raw material was deleted successfully");
    }

    @Override
    public List<RawMaterialDTOResponse> getByLessThenOrEqualStock(Integer stock) {
        List<RawMaterial> rawMaterials = rawMaterialRepository.findRawMaterialByStockLessThanEqual(stock);
        List<RawMaterialDTOResponse> rawMaterialDTOResponses = rawMaterials.stream().map(rawMaterialMapper::toResponseDto).toList();
        return rawMaterialDTOResponses;
    }

    @Override
    public List<RawMaterialDTOResponse> getByGreaterThenOrEqualStock(Integer stock) {
        List<RawMaterial> rawMaterials = rawMaterialRepository.findRawMaterialByStockGreaterThanEqual(stock);
        List<RawMaterialDTOResponse> rawMaterialDTOResponses = rawMaterials.stream().map(rawMaterialMapper::toResponseDto).toList();
        return rawMaterialDTOResponses;
    }
}
