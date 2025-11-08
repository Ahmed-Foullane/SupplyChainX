package com.example.demo.supplier.entity;


import com.example.demo.production.entity.BillOfMaterial;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;

    private String name;
    private Integer stock;
    private Integer stockMin;
    private String unit;

    @ManyToMany(mappedBy = "rawMaterials")
    private List<Supplier> suppliers = new ArrayList<>();

    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL)
    private List<RawMaterialSupplyOrder> rawMaterialSupplyOrders;

    @OneToMany(mappedBy = "rawMaterial")
    private List<BillOfMaterial> billOfMaterials;
}
