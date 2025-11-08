package com.example.demo.production.controller;

import com.example.demo.ApiResponse.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseMessage> handleRuntimeException(RuntimeException e){
        ResponseMessage errorMessage = new ResponseMessage(e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
