package com.example.test.synchronization_24_12_14.product.application.usecase;

import com.example.test.synchronization_24_12_14.product.application.service.ProductService;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class OptimisticIncrementUseCase {
    private final ProductService productService;

    OptimisticIncrementUseCase(ProductService productService) {
        this.productService = productService;
    }

    public void execute(Long id) throws InterruptedException {
        while(true) {
            try {
                productService.optimisticIncrement(id);

                break;
            }
            catch (ObjectOptimisticLockingFailureException optimisticLockException) {
                Thread.sleep(30);
            }
            catch (Exception e){
                break;
            }
        }
    }
}
