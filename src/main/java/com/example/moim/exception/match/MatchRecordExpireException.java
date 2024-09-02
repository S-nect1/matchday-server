package com.example.moim.exception.match;

public class MatchRecordExpireException extends RuntimeException {
    public MatchRecordExpireException(String message) {
        super(message);
    }

    public MatchRecordExpireException() {
        super("매치 득점 기록 입력 기간이 지났습니다.");
    }
}
