package com.example.gift.request;

public class GiftFcfsRequest {

    private final Long userId;

    private final Long giftId;

    public GiftFcfsRequest(Long userId, Long giftId) {
        this.userId = userId;
        this.giftId = giftId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGiftId() {
        return giftId;
    }
}
