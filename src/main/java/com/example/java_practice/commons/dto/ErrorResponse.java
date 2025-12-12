package com.example.java_practice.commons.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * API 에러 응답 DTO
 * */
@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
