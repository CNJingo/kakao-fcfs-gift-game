package com.example.gift.service;

import com.example.gift.domain.Gift;
import com.example.gift.domain.Member;
import com.example.gift.exception.GiftNotFoundException;
import com.example.gift.exception.NotEnoughGiftException;
import com.example.gift.producer.GiftCreateProducer;
import com.example.gift.producer.GiftRequest;
import com.example.gift.repository.GiftCountRepository;
import com.example.gift.repository.GiftRepository;
import com.example.gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GiftService {


    private final GiftCountRepository giftCountRepository;

    private final GiftRepository giftRepository;

    private final MemberRepository memberRepository;

    private final GiftCreateProducer giftCreateProducer;

    public GiftService(GiftCountRepository giftCountRepository, GiftRepository giftRepository, MemberRepository memberRepository, GiftCreateProducer giftCreateProducer) {
        this.giftCountRepository = giftCountRepository;
        this.giftRepository = giftRepository;
        this.memberRepository = memberRepository;
        this.giftCreateProducer = giftCreateProducer;
    }

    @Transactional(readOnly = true)
    public void firstComeFirstServedGiftDistribution(Long userId, Long giftId, int giftStock) {
        giftRepository.findById(giftId)
                .orElseThrow(() -> new GiftNotFoundException("{" + giftId + "} 에 해당하는 선물이 존재하지 않습니다."));

        long count = giftCountRepository.increment(String.valueOf(giftId));

        if (count > giftStock) {
            throw new NotEnoughGiftException("선착순 게임이 종료 되었습니다.");
        }

        GiftRequest giftRequest = new GiftRequest(String.valueOf(userId), String.valueOf(giftId));

        giftCreateProducer.create(giftRequest);
    }

}
