package com.example.test.lock.application.repository;

public interface RedisLockRepository {
    public Boolean acquireLock(String key);
    public void releaseLock(String key);
}
