package com.example.test.synchronization_24_12_14.lock.application.repository;

public interface RedisLockRepository {
    public Boolean acquireLock(String key);
    public void releaseLock(String key);
}
