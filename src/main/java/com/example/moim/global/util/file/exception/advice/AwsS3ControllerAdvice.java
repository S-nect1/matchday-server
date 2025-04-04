package com.example.moim.global.util.file.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class AwsS3ControllerAdvice extends GeneralException {
    public AwsS3ControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
}
