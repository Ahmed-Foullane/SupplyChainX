package com.example.demo.production.service.impl;

import com.example.demo.ApiResponse.ResponseMessage;
import com.example.demo.production.DTO.ProductDTO;
import com.example.demo.production.DTO.response.product.ProductDTOResponse;
import com.example.demo.production.entity.BillOfMaterial;
import com.example.demo.production.entity.Product;
import com.example.demo.production.mapper.IProductMapper;
import com.example.demo.production.repository.IBillOfMaterialRepository;
import com.example.demo.production.repository.IProductRepository;
import com.example.demo.production.service.IProductService;
import com.example.demo.supplier.entity.RawMaterial;
import com.example.demo.supplier.repository.IRawMaterialRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {
    private final IRawMaterialRepository rawMaterialRepository;
    private final IBillOfMaterialRepository billOfMaterialRepository;
    private final IProductRepository productRepository;
    private final IProductMapper productMapper;
    @Override
    @Transactional
    public ProductDTOResponse createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        List<BillOfMaterial> billOfMaterials = new ArrayList<>();
        for (Map.Entry<Long,Integer> entry: productDTO.rawMaterials().entrySet()){
            Long materialId = entry.getKey();
            Integer quantity = entry.getValue();
            RawMaterial material = rawMaterialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Raw Material ID not found: " + materialId));
            BillOfMaterial bom = new BillOfMaterial();
            bom.setQuantity(quantity);
            bom.setRawMaterial(material);
            bom.setProduct(product);
            billOfMaterials.add(bom);
        }
        product.setBillOfMaterials(billOfMaterials);

        Product product1 = productRepository.save(product);
        ProductDTOResponse productDTOResponse = productMapper.toDtoResponse(product1);
        return productDTOResponse;
    }

    @Override
    public ProductDTOResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("the product id not found"));
        return productMapper.toDtoResponse(product);
    }

    @Override
    public List<ProductDTOResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDtoResponse).toList();
    }

    @Override
    public ResponseMessage deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException(("the product id not found: " + id)));
        if (!product.getOrders().isEmpty()){
            return new ResponseMessage("you can't delete this product because it's linked order");
        }
        productRepository.delete(product);
        return new ResponseMessage("product deleted successfully");
    }

    @Override
    public ProductDTOResponse searchProductByName(String name) {
       Product product = productRepository.findProductByNameContainingIgnoreCase(name);
        return productMapper.toDtoResponse(product);
    }

    @Override
    @Transactional
    public ProductDTOResponse updateProduct(ProductDTO dto, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The product id not found: " + id));

        product.setName(dto.name());
        product.setCost(dto.cost());
        product.setProductionTime(dto.productionTime());
        product.setStock(dto.stock());
        product.getBillOfMaterials().clear();


        List<BillOfMaterial> newBillOfMaterials = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : dto.rawMaterials().entrySet()) {
            Long materialId = entry.getKey();
            Integer quantity = entry.getValue();
            RawMaterial material = rawMaterialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Raw Material ID not found: " + materialId));
            BillOfMaterial bom = new BillOfMaterial();
            bom.setQuantity(quantity);
            bom.setRawMaterial(material);
            bom.setProduct(product);
            newBillOfMaterials.add(bom);
        }

        product.getBillOfMaterials().addAll(newBillOfMaterials);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDtoResponse(updatedProduct);
    }
}
