package com.example.demo.production.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductionOrderDTO;
import com.example.demo.production.DTO.response.productionOrder.ProductionOrderDTOResponse;
import com.example.demo.production.entity.BillOfMaterial;
import com.example.demo.production.entity.Product;
import com.example.demo.production.entity.ProductionOrder;
import com.example.demo.production.entity.enume.ProductionOrderStatus;
import com.example.demo.production.mapper.IProductionOrderMapper;
import com.example.demo.production.repository.IBillOfMaterialRepository;
import com.example.demo.production.repository.IProductRepository;
import com.example.demo.production.repository.IProductionOrderRepository;
import com.example.demo.production.service.IProductionOrderService;
import com.example.demo.supplier.entity.RawMaterial;
import com.example.demo.supplier.repository.IRawMaterialRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.function.FailableObjDoubleConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@Service
public class ProductoinOrderServiceImpl  implements IProductionOrderService {

    private final IProductionOrderRepository productionOrderRepository;
    private final IProductionOrderMapper productionOrderMapper;
    private final IProductRepository productRepository;
    private final IRawMaterialRepository rawMaterialRepository;
    @Override
    public ProductionOrderDTOResponse createProductionOrder(ProductionOrderDTO dto) {

        Product product =  productRepository.findById(dto.productId()).orElseThrow(()-> new RuntimeException("the product id not found: " + dto.productId()));
        List<BillOfMaterial> billOfMaterials = product.getBillOfMaterials();
        for (BillOfMaterial bom: billOfMaterials){
            int quantity = bom.getQuantity() * dto.quantity();
            RawMaterial rawMaterial = bom.getRawMaterial();
            Integer rawMaterialStock = rawMaterial.getStock();
            if (quantity > rawMaterialStock){
                throw new RuntimeException("the stock is not enough to produce this product");
            }
        }

        for (BillOfMaterial bom: billOfMaterials){
            int quantity = bom.getQuantity() * dto.quantity();
            RawMaterial rawMaterial = bom.getRawMaterial();
            rawMaterial.setStock(rawMaterial.getStock()-quantity);
            rawMaterialRepository.save(rawMaterial);
        }

        ProductionOrder productionOrder = productionOrderMapper.toEntity(dto);
        productionOrder.setProduct(product);
        productionOrder.setEndDate(productionOrder.getStartDate().plusHours((long) product.getProductionTime() * dto.quantity()));
        productionOrder.setProductionOrderStatus(ProductionOrderStatus.EN_ATTENTE);
        return productionOrderMapper.toDtoResponse(productionOrderRepository.save(productionOrder));
    }

    @Override
    public ProductionOrderDTOResponse updateProductionOrder(ProductionOrderDTO dto, Long id) {
        ProductionOrder productionOrder = productionOrderRepository.findById(id).orElseThrow(()-> new RuntimeException("the production id not found: " + id));
        Product product = productRepository.findById(dto.productId()).orElseThrow(()-> new RuntimeException("the product id not found"));
        productionOrder.setQuantity(dto.quantity());
        productionOrder.setStartDate(dto.startDate());
        productionOrder.setEndDate(productionOrder.getStartDate().plusHours((long) product.getProductionTime() * dto.quantity()));
        productionOrder.setProduct(product);
        productionOrder.setProductionOrderStatus(ProductionOrderStatus.EN_ATTENTE);
        return productionOrderMapper.toDtoResponse(productionOrderRepository.save(productionOrder));
    }

    @Override
    public ResponseMessage cancelProductionOrder(Long id) {


        ProductionOrder productionOrder = productionOrderRepository.findById(id).orElseThrow(()-> new RuntimeException("the production order not found: " + id));
        if (productionOrder.getProductionOrderStatus().equals(ProductionOrderStatus.EN_ATTENTE)){
            productionOrder.setProductionOrderStatus(ProductionOrderStatus.BLOQUE);
            productionOrderRepository.save(productionOrder);
            return new ResponseMessage("the product order is canceled successfully");
        }
        if (productionOrder.getProductionOrderStatus().equals(ProductionOrderStatus.BLOQUE)){
            return new ResponseMessage("the product order is already canceled");
        }
        if (productionOrder.getProductionOrderStatus().equals(ProductionOrderStatus.EN_PRODUCTION)){
            return new ResponseMessage("you can't cancel the order because it's in production");
        }
        return new ResponseMessage("you can't cancel the production order because is finished");
    }

    @Override
    public List<ProductionOrderDTOResponse> getAllProductionOrders() {
        List<ProductionOrder> productionOrders = productionOrderRepository.findAll();
        return productionOrders.stream().map(productionOrderMapper::toDtoResponse).toList();

    }

    @Override
    public List<ProductionOrderDTOResponse> getProductionOrdersByStatus(ProductionOrderStatus status) {
        List<ProductionOrder> productionOrders = productionOrderRepository.findProductionOrderByProductionOrderStatus(status);
        return productionOrders.stream().map(productionOrderMapper::toDtoResponse).toList();
    }

    @Override
    public ProductionOrderDTOResponse getProductionOrderById(Long id) {
       ProductionOrder productionOrder = productionOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("the production order id not found: " + id));
        return  productionOrderMapper.toDtoResponse(productionOrder);
    }

    @Override
    public long calculateProductionTimeEstimation(Long id) {
        ProductionOrder productionOrder = productionOrderRepository.findById(id).orElseThrow(()-> new RuntimeException("the production order id not found: " + id));
        Duration duration = Duration.between(productionOrder.getStartDate(), productionOrder.getEndDate());
        return duration.toHours();
    }
}
