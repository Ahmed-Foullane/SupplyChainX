package com.example.demo.ApiResponse;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseMessage {
    private String message;
    private LocalDateTime date;
    public ResponseMessage(String message) {
        this.message = message;
        this.date = LocalDateTime.now();
    }
}