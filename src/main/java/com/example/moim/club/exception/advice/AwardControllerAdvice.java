package com.example.moim.club.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class AwardControllerAdvice extends GeneralException {
    public AwardControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
