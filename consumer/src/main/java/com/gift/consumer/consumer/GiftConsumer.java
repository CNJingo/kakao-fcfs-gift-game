package com.gift.consumer.consumer;

import com.gift.consumer.domain.Gift;
import com.gift.consumer.domain.Member;
import com.gift.consumer.repository.GiftRepository;
import com.gift.consumer.repository.MemberRepository;
import com.gift.consumer.request.GiftRequest;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GiftConsumer {

    private final MemberRepository memberRepository;

    private final GiftRepository giftRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftConsumer.class);

    public GiftConsumer(MemberRepository memberRepository, GiftRepository giftRepository) {
        this.memberRepository = memberRepository;
        this.giftRepository = giftRepository;
    }

    @KafkaListener(topics = "send_gift", groupId = "group_1")
    @Transactional
    public void listener(GiftRequest giftRequest) {
        LOGGER.info("선물 보내기 요청이 도착했습니다.");

        final Long userId = Long.parseLong(giftRequest.getUserId());
        final Long giftId = Long.parseLong(giftRequest.getGiftId());

        final Member member = memberRepository.findById(userId).
                orElseThrow(() -> new EntityNotFoundException("유저 id " + ": giftRequest.getUserId()" + " 에 해당하는 유저가 존재하지 않습니다."));

        final Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new EntityNotFoundException("{" + giftId + "} 에 해당하는 선물이 존재하지 않습니다."));

        if (member.isDuplicateGift(gift)) {
            throw new IllegalArgumentException("하나의 선물을 중복해서 받을 수 없습니다."
                    + "user id " + giftRequest.getUserId() + ", gift id" + giftRequest.getGiftId());
        };

        member.receiveGift(gift);
        memberRepository.save(member);
    }
}
