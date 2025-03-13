package com.example.moim.club.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class ClubControllerAdvice extends GeneralException {
    public ClubControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
