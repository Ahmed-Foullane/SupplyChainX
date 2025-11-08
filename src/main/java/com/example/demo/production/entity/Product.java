package com.example.demo.production.entity;

import com.example.demo.customer.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer productionTime;
    private Double cost;
    private Integer stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductionOrder> productionOrders = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BillOfMaterial> billOfMaterials = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();
}
