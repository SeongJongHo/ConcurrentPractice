package com.example.test.synchronization_24_12_14.product.application.repository;

import com.example.test.synchronization_24_12_14.product.domain.model.Product;

public interface ProductRepository {
    public Long save(Product product);
    public Product findById(Long id);
    public Product findByIdWithOptimistic(Long id);
    public Product findByIdWithPessimistic(Long id);
}
