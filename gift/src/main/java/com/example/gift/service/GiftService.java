package com.example.gift.service;

import com.example.gift.exception.GiftNotFoundException;
import com.example.gift.exception.NotEnoughGiftException;
import com.example.gift.producer.GiftCreateProducer;
import com.example.gift.producer.GiftRequest;
import com.example.gift.repository.AppliedMemberRepository;
import com.example.gift.repository.GiftCountRepository;
import com.example.gift.repository.GiftRepository;
import com.example.gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GiftService {


    private final GiftCountRepository giftCountRepository;

    private final GiftRepository giftRepository;

    private final GiftCreateProducer giftCreateProducer;

    private final AppliedMemberRepository appliedMemberRepository;

    public GiftService(GiftCountRepository giftCountRepository, GiftRepository giftRepository, GiftCreateProducer giftCreateProducer, AppliedMemberRepository appliedMemberRepository) {
        this.giftCountRepository = giftCountRepository;
        this.giftRepository = giftRepository;
        this.giftCreateProducer = giftCreateProducer;
        this.appliedMemberRepository = appliedMemberRepository;
    }

    @Transactional(readOnly = true)
    public void firstComeFirstServedGiftDistribution(Long userId, Long giftId, int giftStock) {
        final Long apply = appliedMemberRepository.add(userId);

        if (apply != 1) {
            throw new IllegalArgumentException("유저는 선물을 중복해서 받을 수 없습니다.");
        }

        long count = giftCountRepository.increment(String.valueOf(giftId));

        if (count > giftStock) {
            throw new NotEnoughGiftException("선착순 게임이 종료 되었습니다.");
        }

        GiftRequest giftRequest = new GiftRequest(String.valueOf(userId), String.valueOf(giftId));

        giftCreateProducer.create(giftRequest);
    }

}
