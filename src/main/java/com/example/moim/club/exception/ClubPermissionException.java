package com.example.moim.club.exception;

public class ClubPermissionException extends RuntimeException{
    public ClubPermissionException(String message) {
        super(message);
    }

    public ClubPermissionException() {
        super("모임 권한이 없습니다.");
    }
}
