package com.example.moim.config.jwt.exception.handler;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class JwtExceptionAdvice extends GeneralException {
    public JwtExceptionAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
