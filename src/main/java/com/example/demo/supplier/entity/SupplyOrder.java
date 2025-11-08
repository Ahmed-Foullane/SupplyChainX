package com.example.demo.supplier.entity;

import com.example.demo.supplier.entity.enume.SupplyOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SupplyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private SupplyOrderStatus status;

    @OneToMany(mappedBy = "supplyOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RawMaterialSupplyOrder> rawMaterialSupplyOrders = new ArrayList<>();

    public void addRawMaterialSupplyOrder(RawMaterialSupplyOrder item) {
        this.rawMaterialSupplyOrders.add(item);
        item.setSupplyOrder(this);
    }
}
