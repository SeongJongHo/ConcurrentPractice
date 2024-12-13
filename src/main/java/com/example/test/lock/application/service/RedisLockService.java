package com.example.test.lock.application.service;

import com.example.test.lock.application.repository.RedisLockRepository;
import org.springframework.stereotype.Service;

@Service
public class RedisLockService {
    private final RedisLockRepository redisLockRepository;

    RedisLockService(RedisLockRepository redisLockRepository1) {
        this.redisLockRepository = redisLockRepository1;
    }

    public Boolean acquireLock(Long id) {
        return redisLockRepository.acquireLock(genProductKey(id));
    }

    public void releaseLock(Long id) {
        redisLockRepository.releaseLock(genProductKey(id));
    }

    public String genProductKey(Long id) {
        return RedisKey.PRODUCT.getValue() + id;
    }
}

enum RedisKey {
    PRODUCT("productId:");

    private final String value;

    RedisKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
