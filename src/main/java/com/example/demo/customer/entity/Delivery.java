package com.example.demo.customer.entity;


import com.example.demo.customer.entity.enume.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String vehicle;
    private String driver;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    private LocalDateTime deliveryDate;
    private Double cost;
}
