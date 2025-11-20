package com.example.demo.supplier.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.Supplier;
import com.example.demo.supplier.entity.SupplyOrder;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.mapper.ISupplierMapper;
import com.example.demo.supplier.repository.ISupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

    @Mock
    private ISupplierRepository supplierRepository;

    @Mock
    private ISupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    void createSupplier() {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setContact("8213738");
        supplierDTO.setLastName("foullane");
        supplierDTO.setLeadTime(2);
        supplierDTO.setRating(7.1);

        Supplier supplier = new Supplier();
        supplier.setContact("8213738");
        supplier.setLastName("foullane");
        supplier.setLeadTime(2);
        supplier.setRating(7.1);

        Mockito.when(supplierMapper.toEntity(supplierDTO)).thenReturn(supplier);
        Mockito.when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(supplierDTO);

        assertNotNull(createdSupplier);
        Assertions.assertEquals(supplierDTO.getRating(), createdSupplier.getRating());
        Mockito.verify(supplierMapper).toEntity(supplierDTO);
        Mockito.verify(supplierRepository).save(supplier);
    }

    @Test
    void updateSupplier() {
        Long id = 1L;
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setFirstName("Ahmed");
        supplierDTO.setLastName("Foullane");
        supplierDTO.setContact("12345");
        supplierDTO.setLeadTime(5);
        supplierDTO.setRating(8.5);

        Supplier existingSupplier = new Supplier();
        existingSupplier.setFirstName("Old");
        existingSupplier.setLastName("Name");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setFirstName("Ahmed");
        updatedSupplier.setLastName("Foullane");
        updatedSupplier.setContact("12345");
        updatedSupplier.setLeadTime(5);
        updatedSupplier.setRating(8.5);

        SupplierDTO updatedDTO = new SupplierDTO();
        updatedDTO.setFirstName("Ahmed");
        updatedDTO.setLastName("Foullane");
        updatedDTO.setContact("12345");
        updatedDTO.setLeadTime(5);
        updatedDTO.setRating(8.5);

        Mockito.when(supplierRepository.findById(id)).thenReturn(Optional.of(existingSupplier));
        Mockito.when(supplierRepository.save(existingSupplier)).thenReturn(updatedSupplier);
        Mockito.when(supplierMapper.toDTO(updatedSupplier)).thenReturn(updatedDTO);

        SupplierDTO result = supplierService.updateSupplier(supplierDTO, id);

        assertNotNull(result);
        Assertions.assertEquals("Ahmed", result.getFirstName());
        Assertions.assertEquals("Foullane", result.getLastName());
        Mockito.verify(supplierRepository).findById(id);
        Mockito.verify(supplierRepository).save(existingSupplier);
    }

    @Test
    void deleteSupplier_whenNoActiveOrders_shouldDelete() {
        Long id = 1L;
        Supplier supplier = new Supplier();
        supplier.setOrders(Collections.emptyList());

        Mockito.when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));

        ResponseMessage response = supplierService.deleteSupplier(id);

        Assertions.assertEquals("Supplier deleted successfully.", response.getMessage());
        Mockito.verify(supplierRepository).delete(supplier);
    }

    @Test
    void deleteSupplier_whenHasActiveOrders_shouldNotDelete() {
        Long id = 1L;
        SupplyOrder order = new SupplyOrder();
        order.setStatus(SupplyOrderStatus.EN_COURS);

        Supplier supplier = new Supplier();
        supplier.setOrders(List.of(order));

        Mockito.when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));

        ResponseMessage response = supplierService.deleteSupplier(id);

        Assertions.assertTrue(response.getMessage().contains("can't delete"));
        Mockito.verify(supplierRepository, Mockito.never()).delete(supplier);
    }

    @Test
    void searchSupplierByFirstNameAndLastName_found() {
        Supplier supplier = new Supplier();
        supplier.setFirstName("Ahmed");
        supplier.setLastName("Foullane");

        SupplierDTOResponse responseDTO = new SupplierDTOResponse();
        responseDTO.setFirstName("Ahmed");
        responseDTO.setLastName("Foullane");

        Mockito.when(supplierRepository
                        .findSupplierByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase("Ahmed", "Foullane"))
                .thenReturn(supplier);

        Mockito.when(supplierMapper.toResponseDTO(supplier)).thenReturn(responseDTO);

        SupplierDTOResponse result = supplierService.searchSupplierByFirstNameAndLastName("Ahmed", "Foullane");

        assertNotNull(result);
        Assertions.assertEquals("Ahmed", result.getFirstName());
        Assertions.assertEquals("Foullane", result.getLastName());
    }

    @Test
    void searchSupplierByFirstNameAndLastName_notFound() {
        Mockito.when(supplierRepository
                        .findSupplierByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase("X", "Y"))
                .thenReturn(null);

        Assertions.assertThrows(RuntimeException.class,
                () -> supplierService.searchSupplierByFirstNameAndLastName("X", "Y"));
    }

    @Test
    void getSupplierById_found() {
        Long id = 1L;
        Supplier supplier = new Supplier();
        SupplierDTOResponse dtoResponse = new SupplierDTOResponse();
        dtoResponse.setFirstName("ahmed");

        Mockito.when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
        Mockito.when(supplierMapper.toResponseDTO(supplier)).thenReturn(dtoResponse);

        SupplierDTOResponse result = supplierService.getSupplierById(id);

        assertNotNull(result);
        Assertions.assertEquals("ahmed", result.getFirstName());
    }

    @Test
    void getAllSuppliers_shouldReturnList() {
        Supplier supplier = new Supplier();
        supplier.setFirstName("ahmed");

        SupplierDTOResponse dto = new SupplierDTOResponse();
        dto.setFirstName("ahmed");

        Mockito.when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        Mockito.when(supplierMapper.toResponseDTO(supplier)).thenReturn(dto);

        List<SupplierDTOResponse> result = supplierService.getAllSuplliers();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("ahmed", result.get(0).getFirstName());
    }
}
