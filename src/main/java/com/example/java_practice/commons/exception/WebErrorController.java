package com.example.java_practice.commons.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebErrorController implements ErrorController {
    // 에러 페이지 설정
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request)
    {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) return "error/404";
            else if (statusCode == HttpStatus.BAD_REQUEST.value()) return "error/400";
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) return "error/500";
            else return "error/error";
        }
        return "error/error";
    }
}
