package com.example.demo.customer.entity;


import com.example.demo.customer.entity.enume.OrderStatus;
import com.example.demo.production.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
