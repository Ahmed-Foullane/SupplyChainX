package com.example.demo.supplier.service.impl;


import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplyOrderDTO;
import com.example.demo.supplier.DTO.response.SupplierOrder.SupplierOrderDTOResponse;
import com.example.demo.supplier.entity.RawMaterial;
import com.example.demo.supplier.entity.RawMaterialSupplyOrder;
import com.example.demo.supplier.entity.Supplier;
import com.example.demo.supplier.entity.SupplyOrder;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.mapper.ISupplyOrderMapper;
import com.example.demo.supplier.repository.IRawMaterialRepository;
import com.example.demo.supplier.repository.ISupplierOrderRepository;
import com.example.demo.supplier.repository.ISupplierRepository;
import com.example.demo.supplier.service.ISupplierOrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class SupplierOrderServiceImpl implements ISupplierOrderService {
    private final ISupplierOrderRepository supplierOrderRepository;
    private final ISupplyOrderMapper supplyOrderMapper;
    private final ISupplierRepository supplierRepository;
    private final IRawMaterialRepository rawMaterialRepository;
    @Override
    public List<SupplierOrderDTOResponse> getAllSuplliersOrders() {
        List<SupplyOrder> supplyOrders = supplierOrderRepository.findAll();
        List<SupplierOrderDTOResponse> supplierOrderDTOResponse = supplyOrders.stream().map(supplyOrderMapper::toResponse).toList();
        return supplierOrderDTOResponse;
    }


    @Override
    public SupplierOrderDTOResponse getSupplierOrderById(Long id) {
        SupplyOrder supplyOrder = supplierOrderRepository.findById(id).orElseThrow(()-> new RuntimeException("id not found"));
        SupplierOrderDTOResponse supplierOrderDTOResponse = supplyOrderMapper.toResponse(supplyOrder);
        return supplierOrderDTOResponse;
    }

    @Override
    @Transactional
    public SupplierOrderDTOResponse createSupplierOrder(SupplyOrderDTO dto) {

        Supplier supplier = supplierRepository.findById(dto.supplierId())
                .orElseThrow(() -> new RuntimeException("Supplier ID not found: " + dto.supplierId()));

        SupplyOrder supplyOrder = new SupplyOrder();
        supplyOrder.setSupplier(supplier);
        supplyOrder.setOrderDate(dto.orderDate());
        supplyOrder.setStatus(SupplyOrderStatus.EN_ATTENTE);

        for (Map.Entry<Long, Integer> entry : dto.items().entrySet()) {
            Long materialId = entry.getKey();
            Integer quantity = entry.getValue();
            RawMaterial material = rawMaterialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Raw Material ID not found: " + materialId));
            RawMaterialSupplyOrder lineItem = new RawMaterialSupplyOrder();
            material.setStock(material.getStock() + quantity);
            lineItem.setRawMaterial(material);
            lineItem.setQuantity(quantity);
            supplyOrder.addRawMaterialSupplyOrder(lineItem);
        }

        SupplyOrder savedOrder = supplierOrderRepository.save(supplyOrder);

        return supplyOrderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional
    public SupplierOrderDTOResponse updateSupplierOrder(SupplyOrderDTO supplyOrderDTO, Long id) {
        SupplyOrder supplyOrder = supplierOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("supplier id not found: " + id));
        Supplier supplier = supplierRepository.findById(supplyOrderDTO.supplierId()).orElseThrow(()-> new RuntimeException("the supplier id not found: " + id));
        if (supplyOrder.getStatus().equals(SupplyOrderStatus.RECUE)){
            throw new RuntimeException("you can't update this order because it's already delivered");
        }
        supplyOrder.setSupplier(supplier);
        supplyOrder.setOrderDate(supplyOrderDTO.orderDate());
        supplyOrder.getRawMaterialSupplyOrders().clear();
        for (Map.Entry<Long, Integer> entry : supplyOrderDTO.items().entrySet()) {
            Long materialId = entry.getKey();
            Integer quantity = entry.getValue();
            RawMaterial material = rawMaterialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Raw Material ID not found: " + materialId));
            RawMaterialSupplyOrder lineItem = new RawMaterialSupplyOrder();
            lineItem.setRawMaterial(material);
            lineItem.setQuantity(quantity);
            supplyOrder.addRawMaterialSupplyOrder(lineItem);
        }
        SupplyOrder savedOrder = supplierOrderRepository.save(supplyOrder);

        return supplyOrderMapper.toResponse(savedOrder);

    }

    @Override
    public ResponseMessage deleteSupplierOrder(Long id) {
        SupplyOrder supplyOrder = supplierOrderRepository.findById(id).orElseThrow(()-> new RuntimeException("the id not found: " + id));
        if (!supplyOrder.getStatus().equals(SupplyOrderStatus.RECUE)){
            supplierOrderRepository.delete(supplyOrder);
            return new ResponseMessage("supplier deleted successfully");
        }else {
            return new ResponseMessage("you can't delete this order because it's not yet delivered");
        }
    }

    @Override
    public List<SupplierOrderDTOResponse> getSupplierOrdersByStatus(SupplyOrderStatus status) {
        List<SupplyOrder> supplyOrders = supplierOrderRepository.findSupplyOrderByStatus(status);
        List<SupplierOrderDTOResponse> supplierOrderDTOResponses = supplyOrders.stream().map(supplyOrderMapper::toResponse).toList();
        return supplierOrderDTOResponses;
    }
}
