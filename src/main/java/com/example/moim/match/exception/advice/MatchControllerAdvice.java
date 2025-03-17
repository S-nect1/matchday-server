package com.example.moim.match.exception.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class MatchControllerAdvice extends GeneralException {

    public MatchControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> MatchSaveExHandle(MatchPermissionException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> MatchRecordExpireExHandle(MatchRecordExpireException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.BAD_REQUEST);
//    }
}
