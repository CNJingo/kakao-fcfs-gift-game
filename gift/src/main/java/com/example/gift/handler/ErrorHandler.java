package com.example.gift.handler;

import com.example.gift.exception.DuplicateGiftException;
import com.example.gift.exception.NotEnoughGiftException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEnoughGiftException.class)
    public ResponseEntity<?> handleNotEnoughGiftException(RuntimeException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler(DuplicateGiftException.class)
    public ResponseEntity<?> handleDuplicateGiftException(RuntimeException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}
