package com.example.demo.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Order> orders;
    private String City;
}
