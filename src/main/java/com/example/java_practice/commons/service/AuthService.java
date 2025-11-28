package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.UserWithAuth;

public interface AuthService {
    UserWithAuth getUserInfoById(String id);
    void updateLastLoginByUserNo(int userNo);
}
