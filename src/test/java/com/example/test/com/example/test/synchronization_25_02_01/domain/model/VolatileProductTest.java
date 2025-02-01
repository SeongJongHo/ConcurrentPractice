package com.example.test.com.example.test.synchronization_25_02_01.domain.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.example.test.synchronization_25_02_01.domain.model.product.VolatileProduct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("VolatileProduct 클래스")
public class VolatileProductTest {

    private static final int THREAD_COUNT = 10;
    private static final int INCREMENT_PER_THREAD = 1000;
    private static final int EXPECTED_COUNT = THREAD_COUNT * INCREMENT_PER_THREAD;

    @BeforeEach
    void setUp() {
        System.gc();
    }

    @Nested
    @DisplayName("incrementCount 메서드는")
    class IncrementCountMethod {
        @Test
        @DisplayName("일반 변수를 증가 시키는데 10개의 스레드로 1000번 실행해도 10000이 되지 않는다")
        void should_not_ensure_consistency_with_plain_variable()
            throws InterruptedException, NoSuchFieldException {
            // Given
            VolatileProduct volatileProduct = new VolatileProduct();
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            // When
            for (int i = 0; i < THREAD_COUNT; i++) {
                VolatileProduct finalVolatileProduct = volatileProduct;
                executor.submit(() -> {
                    for (int j = 0; j < INCREMENT_PER_THREAD; j++) {
                        finalVolatileProduct.incrementCount();
                    }
                });
            }
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

            // Then
            assertNotEquals(EXPECTED_COUNT, volatileProduct.getCount());
        }
    }

    @Nested
    @DisplayName("incrementVolatileCount 메서드는")
    class IncrementVolatileCountMethod {
        @Test
        @DisplayName("volatile 변수를 증가 시키는데 10개의 스레드로 1000번 실행해도 10000이 되지 않는다")
        void should_not_ensure_consistency_with_volatile_variable()
            throws InterruptedException, NoSuchFieldException {
            // Given
            VolatileProduct volatileProduct= new VolatileProduct();
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            // When
            for (int i = 0; i < THREAD_COUNT; i++) {
                VolatileProduct finalVolatileProduct = volatileProduct;
                executor.submit(() -> {
                    for (int j = 0; j < INCREMENT_PER_THREAD; j++) {
                        finalVolatileProduct.incrementVolatileCount();
                    }
                });
            }
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

            // Then
            assertEquals(EXPECTED_COUNT, volatileProduct.getVolatileCount());
        }
    }

    @Nested
    @DisplayName("incrementCountCAS 메서드는")
    class IncrementCountCASMethod {
        @Test
        @DisplayName("CAS를 이용하여 일반 변수를 증가 시키는데 10개의 스레드로 1000번 실행하면 10000이 되지 않는다")
        void should_ensure_consistency_with_CAS_plain_variable()
            throws InterruptedException, NoSuchFieldException {
            // Given
            VolatileProduct volatileProduct= new VolatileProduct();
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            // When
            for (int i = 0; i < THREAD_COUNT; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < INCREMENT_PER_THREAD; j++) {
                        volatileProduct.incrementCountCAS();
                    }
                });
            }
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

            // Then
            assertEquals(EXPECTED_COUNT, volatileProduct.getCount());
        }
    }

    @Nested
    @DisplayName("incrementVolatileCountCAS 메서드는")
    class IncrementVolatileCountCASMethod {
        @Test
        @DisplayName("CAS를 이용하여 volatile 변수를 증가 시키는데 10개의 스레드로 1000번 실행하면 10000이 된다")
        void should_ensure_consistency_with_CAS_volatile_variable()
            throws InterruptedException, NoSuchFieldException {
            // Given
            VolatileProduct volatileProduct= new VolatileProduct();
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

            // When
            for (int i = 0; i < THREAD_COUNT; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < INCREMENT_PER_THREAD; j++) {
                        volatileProduct.incrementVolatileCountCAS();
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

            // Then
            assertEquals(EXPECTED_COUNT, volatileProduct.getVolatileCount());
        }
    }
}
