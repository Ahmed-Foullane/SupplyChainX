package com.example.demo.supplier.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.Supplier;
import com.example.demo.supplier.entity.SupplyOrder;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.mapper.ISupplierMapper;
import com.example.demo.supplier.repository.IRawMaterialRepository;
import com.example.demo.supplier.repository.ISupplierRepository;
import com.example.demo.supplier.service.ISupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class SupplierServiceImpl implements ISupplierService {

    private final ISupplierRepository supplierRepository;
    private final ISupplierMapper supplierMapper;

    @Override
    public List<SupplierDTOResponse> getAllSuplliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream().map(supplierMapper::toResponseDTO).toList();
    }

    @Override
    public SupplierDTOResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("supplier id not found: " + id));
        return supplierMapper.toResponseDTO(supplier);
    }

    @Override
    public Supplier createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        return supplierRepository.save(supplier);
    }

    @Override
    public SupplierDTO updateSupplier(SupplierDTO supplierDTO, Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("raw material id not found: " + id));
        supplier.setFirstName(supplierDTO.getFirstName());
        supplier.setLastName(supplierDTO.getLastName());
        supplier.setContact(supplierDTO.getContact());
        supplier.setLeadTime(supplierDTO.getLeadTime());
        supplier.setRating(supplierDTO.getRating());
        Supplier supplier1 = supplierRepository.save(supplier);
        return supplierMapper.toDTO(supplier1);
    }

    @Override
    public ResponseMessage deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier id not found: " + id));
        for (SupplyOrder o : supplier.getOrders()) {
            if (o.getStatus().equals(SupplyOrderStatus.EN_ATTENTE)
                    || o.getStatus().equals(SupplyOrderStatus.EN_COURS)) {
                return new ResponseMessage("You can't delete this supplier because it still has orders that are not yet delivered.");
            }
        }
        supplierRepository.delete(supplier);
        return new ResponseMessage("Supplier deleted successfully.");
    }

    @Override
    public SupplierDTOResponse searchSupplierByFirstNameAndLastName(String firstName, String lastName) {
        Supplier supplier = supplierRepository.findSupplierByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName);
        if (supplier == null) {
            throw new RuntimeException("Supplier with first name '" + firstName + "' and last name '" + lastName + "' not found");
        }
        return supplierMapper.toResponseDTO(supplier);
    }
}