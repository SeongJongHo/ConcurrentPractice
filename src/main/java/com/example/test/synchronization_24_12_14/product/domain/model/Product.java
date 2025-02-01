package com.example.test.synchronization_24_12_14.product.domain.model;

import jakarta.persistence.*;

import java.util.concurrent.atomic.AtomicLong;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AtomicLong atomicQuantity = new AtomicLong(0);

    @Version
    private Long quantity;

    public Product() {}

    public Product(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Product(Long quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public Long getAtomicQuantity() {
        return this.atomicQuantity.longValue();
    }

    public void increment() {
        quantity++;
    }

    //synchronized 키워드를 통해  해당 메소드에 락을 걸어 동시성 문제를 해결
    public synchronized void syncIncrement() {
        quantity++;
    }

    //현재 인스턴스를 synchronized블록을 통해 락을 걸어 동시성 문제를 해결
    public void syncBlockIncrement() {
        synchronized(this){
            quantity++;
        }
    }

    //Atomic클래스를 이용할 경우 CAS(Compare And Swap) 연산을 통해 동시성 문제를 해결
    public void atomicIncrement() {
        atomicQuantity.incrementAndGet();
    }
}
