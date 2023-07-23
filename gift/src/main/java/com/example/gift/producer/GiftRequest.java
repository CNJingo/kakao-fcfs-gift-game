package com.example.gift.producer;

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

    @Override
    public String toString() {
        return "GiftRequest{" +
                "userId='" + userId + '\'' +
                ", giftId='" + giftId + '\'' +
                '}';
    }
}
