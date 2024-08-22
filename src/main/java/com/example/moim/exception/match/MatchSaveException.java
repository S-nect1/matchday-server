package com.example.moim.exception.match;

public class MatchSaveException extends RuntimeException{
    public MatchSaveException(String message) {
        super(message);
    }

    public MatchSaveException() {
        super("매치 권한이 없습니다.");
    }
}
