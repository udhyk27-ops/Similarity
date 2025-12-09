package com.example.java_practice.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    // api 에러 응답 DTO
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
