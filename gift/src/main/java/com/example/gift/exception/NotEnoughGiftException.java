package com.example.gift.exception;

public class NotEnoughGiftException extends RuntimeException {
    public NotEnoughGiftException(String message) {
        super(message);
    }
}
