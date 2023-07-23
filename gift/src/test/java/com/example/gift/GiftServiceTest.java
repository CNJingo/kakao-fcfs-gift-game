package com.example.gift;

import com.example.gift.domain.Gift;
import com.example.gift.domain.Member;
import com.example.gift.producer.GiftCreateProducer;
import com.example.gift.repository.GiftCountRepository;
import com.example.gift.repository.GiftRepository;
import com.example.gift.repository.MemberRepository;
import com.example.gift.service.GiftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@GiftTestcontainers
@SpringBootTest
public class GiftServiceTest {

    @Autowired
    private GiftService giftService;

    @Autowired
    private GiftCountRepository giftCountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GiftRepository giftRepository;

    @MockBean
    private GiftCreateProducer giftCreateProducer;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void giftTest() throws Throwable {

        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        List<Member> memberList = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            Member member = memberRepository.save(new Member());
            memberList.add(member);
        }

        Gift gift = new Gift("test", 1000L, memberList.get(0).getId());
        Gift savedGift = giftRepository.save(gift);
        int giftStock = 10;

        for (int i = 0; i < threadCount; i++) {
            final long memberId = i;
            executorService.submit(() -> {
                try {
                    giftService.firstComeFirstServedGiftDistribution(memberId, savedGift.getId(), giftStock);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertEquals(threadCount, Integer.parseInt(giftCountRepository.getValue(String.valueOf(savedGift.getId()))));
        verify(giftCreateProducer,times(giftStock)).create(any());
    }
}
