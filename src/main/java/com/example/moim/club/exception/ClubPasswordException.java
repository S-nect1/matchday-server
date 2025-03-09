package com.example.moim.club.exception;

public class ClubPasswordException extends RuntimeException{
    public ClubPasswordException(String message) {
        super(message);
    }

    public ClubPasswordException() {
        super("비밀번호가 틀렸습니다.");
    }
}
