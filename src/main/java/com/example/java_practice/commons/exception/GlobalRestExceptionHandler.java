package com.example.java_practice.commons.exception;

import com.example.java_practice.commons.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Rest API 예외 처리. custom 필요하면 추가
 * */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalRestExceptionHandler {

    /** JSON 파싱 에러 */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> handleJsonException(JsonProcessingException e){
        log.error("JSON Parsing Error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_JSON", "잘못된 JSON 형식입니다"));
    }

    /** 일반적인 예외 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error("API Error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("ERROR", e.getMessage()));
    }

}
