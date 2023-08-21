package com.example.gift.service;

import com.example.gift.aop.DistributedLock;
import com.example.gift.domain.Gift;
import com.example.gift.exception.DuplicateGiftException;
import com.example.gift.exception.NotEnoughGiftException;
import com.example.gift.producer.GiftCreateProducer;
import com.example.gift.producer.GiftRequest;
import com.example.gift.repository.AppliedMemberRepository;
import com.example.gift.repository.GiftCountRepository;
import com.example.gift.repository.GiftRepository;
import jakarta.persistence.EntityNotFoundException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class GiftService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftService.class);

    private final GiftCountRepository giftCountRepository;

    private final GiftRepository giftRepository;

    private final GiftCreateProducer giftCreateProducer;

    private final AppliedMemberRepository appliedMemberRepository;

    private final RedissonClient redissonClient;

    public GiftService(GiftCountRepository giftCountRepository, GiftRepository giftRepository, GiftCreateProducer giftCreateProducer, AppliedMemberRepository appliedMemberRepository, RedissonClient redissonClient) {
        this.giftCountRepository = giftCountRepository;
        this.giftRepository = giftRepository;
        this.giftCreateProducer = giftCreateProducer;
        this.appliedMemberRepository = appliedMemberRepository;
        this.redissonClient = redissonClient;
    }

    @DistributedLock(key = "gift")
    public void firstComeFirstServedGiftDistribution(Long userId, Long giftId) {

        final Long apply = appliedMemberRepository.add(userId);

        if (apply != 1) {
            throw new DuplicateGiftException("유저는 선물을 중복해서 받을 수 없습니다.");
        }

        LOGGER.info("선착순 선물을 향해 달려갑니다. thread id " + Thread.currentThread().getId() + " 현재 시간 : " + LocalDateTime.now());

        final Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new EntityNotFoundException("gift id : " + giftId + " 에 해당하는 선물이 존재하지 않습니다."));

        System.out.println("선물 잔고는 " + gift.getStock());
        if (gift.getStock() <= 0) {
            throw new NotEnoughGiftException("선착순 게임이 종료 되었습니다.");
        }

        gift.minusStock();
        giftRepository.save(gift);
        GiftRequest giftRequest = new GiftRequest(String.valueOf(userId), String.valueOf(giftId));
        giftCreateProducer.create(giftRequest);
    }
}
