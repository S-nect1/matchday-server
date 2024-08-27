package com.example.moim.match.entity;

public enum MatchStatus {
    //매치 등록시 대기, 등록, 실패
    PENDING, REGISTERED, FAILED,
    //매치 확정
    CONFIRMED
}
