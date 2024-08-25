package com.example.moim.match.entity;

public enum Status {
    //매치 등록시 대기, 등록, 실패
    PENDING, REGISTERED, FAILED,
    //매치 신청시 건의, 대기, 완료
    SUBMITTED, PENDING_APP, APP_COMPLETED, REJECTED,
    //매치 확정
    CONFIRMED
}
