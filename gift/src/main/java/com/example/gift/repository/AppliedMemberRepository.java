package com.example.gift.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedMemberRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public AppliedMemberRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long add(Long memberId) {
        return redisTemplate
                .opsForSet()
                .add("applied_member", memberId.toString());
    }
}
