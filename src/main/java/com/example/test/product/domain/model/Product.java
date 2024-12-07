package com.example.test.product.domain.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long quantity;

    public Product(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
