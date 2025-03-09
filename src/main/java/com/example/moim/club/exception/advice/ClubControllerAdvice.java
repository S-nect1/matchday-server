package com.example.moim.club.exception.advice;

import com.example.moim.club.controller.ClubController;
import com.example.moim.club.exception.ClubPasswordException;
import com.example.moim.club.exception.ClubPermissionException;
import com.example.moim.global.exception.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ClubController.class)
public class ClubControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> MethodArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResult(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> ClubPermissionExHandle(ClubPermissionException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> ClubPasswordExHandle(ClubPasswordException e) {
        return new ResponseEntity<>(new ErrorResult(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
