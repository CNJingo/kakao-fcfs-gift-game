package com.example.gift.request;

public class GiftFcfsRequest {

    private final Long userId;

    private final Long giftId;

    private final Integer giftStock;

    public GiftFcfsRequest(Long userId, Long giftId, Integer giftStock) {
        this.userId = userId;
        this.giftId = giftId;
        this.giftStock = giftStock;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public Integer getGiftStock() {
        return giftStock;
    }
}
