package com.example.test.com.example.test.synchronization_24_12_14.product.application.service;

import com.example.test.synchronization_24_12_14.product.application.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@DisplayName("ProductService 클래스")
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Nested
    @DisplayName("pessimisticIncrement 메소드는")
    class pessimistic_increment {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_pessimisticIncrement를_하더라도_동시성_이슈가_발생하지_않는다")
        public void 여러_스레드에서_동시에_접근해_pessimisticIncrement를_하더라도_동시성_이슈가_발생하지_않는다() throws InterruptedException {
            Long productId = productService.save(0L);
            long expectedQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        productService.pessimisticIncrement(productId);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertEquals(productService.getProduct(productId).getQuantity(), expectedQuantity);
        }
    }

    @Nested
    @DisplayName("increment 메소드는")
    class increment {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_increment를_하면_데이터_정합성에_문제가_생긴다")
        public void 여러_스레드에서_동시에_접근해_increment를_하면_데이터_정합성에_문제가_생긴다() throws InterruptedException {
            Long productId = productService.save(0L);
            long expectedQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        productService.increment(productId);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertNotEquals(productService.getProduct(productId).getQuantity(), expectedQuantity);
        }
    }
}