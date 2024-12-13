package com.example.test.product.application.usecase;

import com.example.test.product.application.service.ProductService;

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
@DisplayName("OptimisticIncrementUseCase 클래스")
public class OptimisticIncrementUseCaseTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private OptimisticIncrementUseCase optimisticIncrementUseCase;

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
                        optimisticIncrementUseCase.execute(productId);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
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
