package com.example.moim.global.enums.exception;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class EnumControllerAdvice extends GeneralException {
    public EnumControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
