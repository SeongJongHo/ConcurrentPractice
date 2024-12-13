package com.example.test.product.application.service;

import com.example.test.product.domain.model.Product;
import com.example.test.product.application.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void increment(Long productId) {
        Product product = productRepository.findById(productId);

        product.increment();

        productRepository.save(product);
    }

    @Transactional
    public void optimisticIncrement(Long productId) {
        Product product = productRepository.findByIdWithOptimistic(productId);

        product.increment();

        productRepository.save(product);
    }

    @Transactional
    public void pessimisticIncrement(Long productId) {
        Product product = productRepository.findByIdWithPessimistic(productId);

        product.increment();

        productRepository.save(product);
    }

    public Long save(Long quantity) {

        return productRepository.save(new Product(quantity));
    }

    public Product getProduct(Long productId) {

        return productRepository.findById(productId);
    }
}
