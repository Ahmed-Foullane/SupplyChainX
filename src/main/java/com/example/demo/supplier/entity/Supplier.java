package com.example.demo.supplier.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSupplier;

    private String firstName;
    private String lastName;
    private String contact;
    private Double rating;
    private Integer leadTime;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplyOrder> orders;

    @ManyToMany
    @JoinTable(
            name = "supplier_raw_material",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "raw_material_id")
    )
    private List<RawMaterial> rawMaterials = new ArrayList<>();
}