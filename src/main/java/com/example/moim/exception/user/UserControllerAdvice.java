package com.example.moim.exception.user;

import com.example.moim.exception.ErrorResult;
import com.example.moim.user.controller.UserController;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> BadCredentialsExHandle(BadCredentialsException e) {
        return new ResponseEntity<>(new ErrorResult("아이디 또는 비밀번호가 틀렸습니다."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> EntityExistsExHandle(EntityExistsException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }
}
