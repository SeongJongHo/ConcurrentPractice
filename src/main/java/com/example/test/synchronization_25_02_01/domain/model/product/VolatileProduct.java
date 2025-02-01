package com.example.test.synchronization_25_02_01.domain.model.product;

import com.example.test.synchronization_25_02_01.common.util.UnsafeUtil;
import sun.misc.Unsafe;

public class VolatileProduct {
    private int count = 0;
    private volatile int volatileCount = 0;

    private static final Unsafe unsafe = UnsafeUtil.getUnsafe();
    private final long countOffset;
    private final long volatileCountOffset;

    public VolatileProduct() throws NoSuchFieldException {
        this.countOffset = unsafe.objectFieldOffset(VolatileProduct.class.getDeclaredField("count"));
        this.volatileCountOffset = unsafe.objectFieldOffset(VolatileProduct.class.getDeclaredField("volatileCount"));
    }

    public void incrementCount() {
        count++;
    }

    public void incrementVolatileCount() {
        volatileCount++;
    }

    public void incrementCountCAS() {
        int oldValue;
        do {
            oldValue = unsafe.getInt(this, countOffset);
        } while (!unsafe.compareAndSwapInt(this, countOffset, oldValue, oldValue + 1));
    }

    public void incrementVolatileCountCAS() {
        int oldValue;
        do {
            oldValue = unsafe.getIntVolatile(this, volatileCountOffset);
        } while (!unsafe.compareAndSwapInt(this, volatileCountOffset, oldValue, oldValue + 1));
    }

    public int getCount() {
        return count;
    }

    public int getVolatileCount() {
        return volatileCount;
    }
}
