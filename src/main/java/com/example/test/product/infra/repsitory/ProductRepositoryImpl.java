package com.example.test.product.infra.repsitory;

import com.example.test.product.domain.model.Product;
import com.example.test.product.application.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Long save(Product product) {
        return jpaProductRepository.saveAndFlush(product).getId();
    }

    @Override
    public Product findById(Long id) {
        return jpaProductRepository.findById(id).orElseThrow();
    }

    @Override
    public Product findByIdWithOptimistic(Long id) {
        return jpaProductRepository.findByIdWithOptimistic(id);
    }

    @Override
    public Product findByIdWithPessimistic(Long id) {
        return jpaProductRepository.findByIdWithPessimistic(id);
    }
}
