package com.example.test.com.example.test.synchronization_24_12_14.product.application.usecase;

import com.example.test.synchronization_24_12_14.product.application.service.ProductService;
import com.example.test.synchronization_24_12_14.product.application.usecase.ProductRedisLockUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("ProductRedisLockUseCaseTest 클래스")
public class ProductRedisLockUseCaseTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRedisLockUseCase productRedisLockUseCase;

    @Nested
    @DisplayName("execute 메소드는")
    class Execute {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_execute를_하더라도_동시성_이슈가_발생하지_않는다")
        public void 여러_스레드에서_동시에_접근해_execute를_하더라도_동시성_이슈가_발생하지_않는다() throws InterruptedException {
            Long productId = productService.save(0L);
            long expectedQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        productRedisLockUseCase.execute(productId);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertEquals(productService.getProduct(productId).getQuantity(), expectedQuantity);
        }
    }
}
