package com.example.moim.exception.club;

import com.example.moim.club.controller.ClubController;
import com.example.moim.exception.ErrorResult;
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
}
