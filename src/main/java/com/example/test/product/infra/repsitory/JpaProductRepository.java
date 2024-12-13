package com.example.test.product.infra.repsitory;

import com.example.test.product.domain.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product,Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Product findByIdWithPessimistic(Long id);

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select p from Product p where p.id = :id")
    Product findByIdWithOptimistic(Long id);
}
