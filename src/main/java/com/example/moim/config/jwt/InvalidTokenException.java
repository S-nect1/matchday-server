package com.example.moim.config.jwt;

public class InvalidTokenException extends RuntimeException {
    
    public InvalidTokenException(String message) {
        super(message);
    }
    
    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다");
    }
}
