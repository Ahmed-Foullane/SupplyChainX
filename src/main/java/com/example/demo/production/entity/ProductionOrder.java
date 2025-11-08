package com.example.demo.production.entity;

import com.example.demo.production.entity.enume.ProductionOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer Quantity;
    @Enumerated(EnumType.STRING)
    private ProductionOrderStatus productionOrderStatus;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
