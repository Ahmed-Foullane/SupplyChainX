package com.example.demo.supplier.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RawMaterialSupplyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    @ManyToOne
    @JoinColumn(name = "supply_order_id")
    private SupplyOrder supplyOrder;
}
