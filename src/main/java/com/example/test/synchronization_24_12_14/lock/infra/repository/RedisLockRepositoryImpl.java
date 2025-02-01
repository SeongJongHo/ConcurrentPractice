package com.example.test.synchronization_24_12_14.lock.infra.repository;

import com.example.test.synchronization_24_12_14.lock.application.repository.RedisLockRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisLockRepositoryImpl implements RedisLockRepository {
    private final RedisTemplate<String, String> redisTemplate;

    RedisLockRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean acquireLock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, "gift", Duration.ofMillis(2000));
    }

    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
