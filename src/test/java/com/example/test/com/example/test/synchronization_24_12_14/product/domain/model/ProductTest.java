package com.example.test.com.example.test.synchronization_24_12_14.product.domain.model;

import com.example.test.synchronization_24_12_14.product.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product 클래스")
public class ProductTest {
    @Nested
    @DisplayName("increment 메소드는")
    class Increment_quantity {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_increment를_하면_데이터_정합성에_문제가_생긴다")
        public void 여러_스레드에서_동시에_접근해_increment를_하면_데이터_정합성에_문제가_생긴다() throws InterruptedException {
            Product product = new Product(1L, 0L);
            long expectQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        product.increment();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertNotEquals(product.getQuantity(), expectQuantity);
        }
    }

    @Nested
    @DisplayName("syncIncrement 메소드는")
    class Sync_Increment_quantity {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_syncIncrement를_하더라도_동시성_이슈가_발생하지_않는다")
        public void 여러_스레드에서_동시에_접근해_syncIncrement를_하더라도_동시성_이슈가_발생하지_않는다() throws InterruptedException {
            Product product = new Product(1L, 0L);
            long expectQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        product.syncIncrement();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertEquals(product.getQuantity(), expectQuantity);
        }
    }

    @Nested
    @DisplayName("syncBlockIncrement 메소드는")
    class Sync_block_Increment_quantity {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_syncBlockIncrement를_하더라도_동시성_이슈가_발생하지_않는다")
        public void 여러_스레드에서_동시에_접근해_syncBlockIncrement를_하더라도_동시성_이슈가_발생하지_않는다() throws InterruptedException {
            Product product = new Product(1L, 0L);
            long expectQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        product.syncBlockIncrement();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertEquals(product.getQuantity(), expectQuantity);
        }
    }

    @Nested
    @DisplayName("atomicIncrement 메소드는")
    class Atomic_Increment_quantity {
        @Test
        @DisplayName("여러_스레드에서_동시에_접근해_atomicIncrement를_하더라도_동시성_이슈가_발생하지_않는다")
        public void 여러_스레드에서_동시에_접근해_atomicIncrement를_하더라도_동시성_이슈가_발생하지_않는다() throws InterruptedException {
            Product product = new Product(1L, 0L);
            long expectQuantity = 100;
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        product.atomicIncrement();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            assertEquals(product.getAtomicQuantity(), expectQuantity);
        }
    }
}
