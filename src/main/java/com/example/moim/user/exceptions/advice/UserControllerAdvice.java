package com.example.moim.user.exceptions.advice;

import com.example.moim.global.exception.GeneralException;
import com.example.moim.global.exception.ResponseCode;

public class UserControllerAdvice extends GeneralException {
    public UserControllerAdvice(ResponseCode responseCode) {
        super(responseCode);
    }
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> BadCredentialsExHandle(BadCredentialsException e) {
//        return new ResponseEntity<>(new ErrorResult("아이디 또는 비밀번호가 틀렸습니다."), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> EntityExistsExHandle(EntityExistsException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
//        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
//    }
}
