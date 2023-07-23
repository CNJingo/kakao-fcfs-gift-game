package com.gift.consumer.request;

public class GiftRequest {

    private final String userId;

    private final String giftId;

    public GiftRequest(String userId, String giftId) {
        this.userId = userId;
        this.giftId = giftId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGiftId() {
        return giftId;
    }
}
