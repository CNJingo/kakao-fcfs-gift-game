package com.example.gift.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class GiftCreateProducer {

    private final KafkaTemplate<String, GiftRequest> kafkaTemplate;

    public GiftCreateProducer(KafkaTemplate<String, GiftRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(GiftRequest giftRequest) {
        kafkaTemplate.send("send_gift", giftRequest);
    }
}
