package com.example.moim.exception.match;

public class MatchPermissionException extends RuntimeException {
    public MatchPermissionException(String message) {
        super(message);
    }

    public MatchPermissionException() {
        super("매치 권한이 없습니다.");
    }
}
