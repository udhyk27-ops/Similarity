package com.example.java_practice.commons.security;

import com.example.java_practice.commons.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 인증 완료 후
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 최신 로그인 갱신
        authService.updateLastLoginByUserNo(userDetails.getUserNo());

        response.sendRedirect("/main");

    }
}
