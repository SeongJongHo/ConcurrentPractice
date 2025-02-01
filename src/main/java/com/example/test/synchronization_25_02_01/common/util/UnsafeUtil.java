package com.example.test.synchronization_25_02_01.common.util;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeUtil {

    private UnsafeUtil() {
    }

    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unsafe instance cannot be obtained", e);
        }
    }
}
