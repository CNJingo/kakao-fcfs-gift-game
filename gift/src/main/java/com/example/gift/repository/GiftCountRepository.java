package com.example.gift.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public GiftCountRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment(String key) {
        return redisTemplate
                .opsForValue()
                .increment(key);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
