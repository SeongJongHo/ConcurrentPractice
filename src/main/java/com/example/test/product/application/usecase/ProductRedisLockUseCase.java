package com.example.test.product.application.usecase;

import com.example.test.lock.application.service.RedisLockService;
import com.example.test.product.application.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductRedisLockUseCase {
    private final RedisLockService redisLockService;
    private final ProductService productService;

    public ProductRedisLockUseCase(RedisLockService redisLockService, ProductService productService) {
        this.redisLockService = redisLockService;
        this.productService = productService;
    }

    public void execute(Long productId) {
        boolean lock = false;

        while(true) {
            try {
                lock = redisLockService.acquireLock(productId);
                if(lock) {
                    productService.increment(productId);

                    break;
                }
                else {
                    Thread.sleep(30);
                }
            } catch (Exception e) {
                break;
            }
        }

        if(lock) redisLockService.releaseLock(productId);
    }
}
