package com.example.java_practice.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 웹 예외처리(try-catch). custom 필요할 경우 추가
 * */
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    /** 400 bad request */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBusinessException(IllegalArgumentException e, Model model){
        log.error("IllegalArgument", e.getMessage());
        model.addAttribute("msg", "잘못된 요청이 발생했습니다");
        return "error/400";
    }

    /** 404 */
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, Model model) {
        log.error("Not Found: {}", e.getMessage());
        model.addAttribute("msg", "페이지를 찾을 수 없습니다");
        return "error/404";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException e, Model model){
        log.error("Runtime", e.getMessage());
        model.addAttribute("msg", "시스템 오류가 발생했습니다");
        return "error/error";
    }

    /** 일반적인 예외 */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model){
        log.error("Unexpected Error", e);
        model.addAttribute("msg", "시스템 오류가 발생했습니다");
        return "error/error";
    }

}
