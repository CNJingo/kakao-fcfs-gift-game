package com.example.gift.exception;

public class GiftNotFoundException extends RuntimeException {

    public GiftNotFoundException(String message) {
        super(message);
    }
}
