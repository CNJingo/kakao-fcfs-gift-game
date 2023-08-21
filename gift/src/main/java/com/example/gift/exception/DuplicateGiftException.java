package com.example.gift.exception;

public class DuplicateGiftException extends RuntimeException {
    public DuplicateGiftException(String message) {
        super(message);
    }
}
