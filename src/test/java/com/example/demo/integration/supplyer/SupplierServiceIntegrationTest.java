package com.example.demo.integration.supplyer;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.supplier.DTO.SupplierDTO;
import com.example.demo.supplier.DTO.response.supplier.SupplierDTOResponse;
import com.example.demo.supplier.entity.Supplier;
import com.example.demo.supplier.entity.SupplyOrder;
import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import com.example.demo.supplier.repository.ISupplierRepository;
import com.example.demo.supplier.service.impl.SupplierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
class SupplierServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private SupplierServiceImpl supplierService;

    @Autowired
    private ISupplierRepository supplierRepository;

    @BeforeEach
    void setUp() {
        supplierRepository.deleteAll();
    }

    @Test
    void shouldCreateSupplierSuccessfully() {
        SupplierDTO supplierDTO = SupplierDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .contact("john.doe@example.com")
                .leadTime(5)
                .rating(4.5)
                .build();

        Supplier createdSupplier = supplierService.createSupplier(supplierDTO);

        assertThat(createdSupplier).isNotNull();
        assertThat(createdSupplier.getFirstName()).isEqualTo("John");
        assertThat(createdSupplier.getLastName()).isEqualTo("Doe");
        assertThat(createdSupplier.getContact()).isEqualTo("john.doe@example.com");
        assertThat(createdSupplier.getLeadTime()).isEqualTo(5);
        assertThat(createdSupplier.getRating()).isEqualTo(4.5);
    }

    @Test
    void shouldGetAllSuppliers() {
        Supplier supplier1 = createAndSaveSupplier("Alice", "Smith", "alice@example.com", 3, 4.0);
        Supplier supplier2 = createAndSaveSupplier("Bob", "Jones", "bob@example.com", 7, 4.8);

        List<SupplierDTOResponse> suppliers = supplierService.getAllSuplliers();

        assertThat(suppliers).hasSize(2);
        assertThat(suppliers).extracting("firstName")
                .containsExactlyInAnyOrder("Alice", "Bob");
    }

    @Test
    void shouldGetSupplierById() {
        Supplier savedSupplier = createAndSaveSupplier("Jane", "Doe", "jane@example.com", 4, 4.2);

        SupplierDTOResponse result = supplierService.getSupplierById(savedSupplier.getIdSupplier());

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getContact()).isEqualTo("jane@example.com");
    }

    @Test
    void shouldThrowExceptionWhenSupplierNotFoundById() {
        Long nonExistentId = 999L;

        assertThatThrownBy(() -> supplierService.getSupplierById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("supplier id not found: " + nonExistentId);
    }

    @Test
    void shouldUpdateSupplierSuccessfully() {
        Supplier savedSupplier = createAndSaveSupplier("Old", "Name", "old@example.com", 5, 3.5);

        SupplierDTO updateDTO = SupplierDTO.builder()
                .firstName("New")
                .lastName("Name")
                .contact("new@example.com")
                .leadTime(10)
                .rating(4.9)
                .build();

        SupplierDTO result = supplierService.updateSupplier(updateDTO, savedSupplier.getIdSupplier());

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("New");
        assertThat(result.getLastName()).isEqualTo("Name");
        assertThat(result.getContact()).isEqualTo("new@example.com");
        assertThat(result.getLeadTime()).isEqualTo(10);
        assertThat(result.getRating()).isEqualTo(4.9);

        Supplier updatedSupplier = supplierRepository.findById(savedSupplier.getIdSupplier()).orElseThrow();
        assertThat(updatedSupplier.getFirstName()).isEqualTo("New");
    }


    @Test
    void shouldDeleteSupplierSuccessfully() {
        Supplier savedSupplier = createAndSaveSupplier("Delete", "Me", "delete@example.com", 6, 4.0);

        ResponseMessage response = supplierService.deleteSupplier(savedSupplier.getIdSupplier());

        assertThat(response.getMessage()).isEqualTo("Supplier deleted successfully.");
        assertThat(supplierRepository.findById(savedSupplier.getIdSupplier())).isEmpty();
    }

    @Test
    void shouldNotDeleteSupplierWithPendingOrders() {
        Supplier supplier = createAndSaveSupplier("Active", "Supplier", "active@example.com", 5, 4.5);

        SupplyOrder pendingOrder = new SupplyOrder();
        pendingOrder.setStatus(SupplyOrderStatus.EN_ATTENTE);
        pendingOrder.setSupplier(supplier);
        supplier.getOrders().add(pendingOrder);
        supplierRepository.save(supplier);

        ResponseMessage response = supplierService.deleteSupplier(supplier.getIdSupplier());

        assertThat(response.getMessage())
                .isEqualTo("You can't delete this supplier because it still has orders that are not yet delivered.");
        assertThat(supplierRepository.findById(supplier.getIdSupplier())).isPresent();
    }

    @Test
    void shouldNotDeleteSupplierWithInProgressOrders() {
        Supplier supplier = createAndSaveSupplier("Busy", "Supplier", "busy@example.com", 5, 4.5);

        SupplyOrder inProgressOrder = new SupplyOrder();
        inProgressOrder.setStatus(SupplyOrderStatus.EN_COURS);
        inProgressOrder.setSupplier(supplier);
        supplier.getOrders().add(inProgressOrder);
        supplierRepository.save(supplier);

        ResponseMessage response = supplierService.deleteSupplier(supplier.getIdSupplier());

        assertThat(response.getMessage())
                .isEqualTo("You can't delete this supplier because it still has orders that are not yet delivered.");
        assertThat(supplierRepository.findById(supplier.getIdSupplier())).isPresent();
    }

    @Test
    void shouldSearchSupplierByFirstNameAndLastName() {
        createAndSaveSupplier("John", "Smith", "john.smith@example.com", 5, 4.3);
        createAndSaveSupplier("Jane", "Doe", "jane.doe@example.com", 6, 4.5);

        SupplierDTOResponse result = supplierService.searchSupplierByFirstNameAndLastName("John", "Smith");

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Smith");
        assertThat(result.getContact()).isEqualTo("john.smith@example.com");
    }

    @Test
    void shouldSearchSupplierByFirstNameAndLastNameIgnoreCase() {
        createAndSaveSupplier("Alice", "Johnson", "alice.j@example.com", 4, 4.7);

        SupplierDTOResponse result = supplierService.searchSupplierByFirstNameAndLastName("alice", "johnson");

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Alice");
        assertThat(result.getLastName()).isEqualTo("Johnson");
    }

    @Test
    void shouldSearchSupplierByPartialFirstNameAndLastName() {
        createAndSaveSupplier("Alexander", "Thompson", "alex.t@example.com", 5, 4.1);

        SupplierDTOResponse result = supplierService.searchSupplierByFirstNameAndLastName("Alex", "Thomp");

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Alexander");
        assertThat(result.getLastName()).isEqualTo("Thompson");
    }

    @Test
    void shouldThrowExceptionWhenSupplierNotFoundByName() {
        assertThatThrownBy(() -> supplierService.searchSupplierByFirstNameAndLastName("NonExistent", "Supplier"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Supplier with first name 'NonExistent' and last name 'Supplier' not found");
    }

    @Test
    void shouldReturnEmptyListWhenNoSuppliersExist() {
        List<SupplierDTOResponse> suppliers = supplierService.getAllSuplliers();

        assertThat(suppliers).isEmpty();
    }

    private Supplier createAndSaveSupplier(String firstName, String lastName, String contact, int leadTime, double rating) {
        Supplier supplier = new Supplier();
        supplier.setFirstName(firstName);
        supplier.setLastName(lastName);
        supplier.setContact(contact);
        supplier.setLeadTime(leadTime);
        supplier.setRating(rating);
        return supplierRepository.save(supplier);
    }
}