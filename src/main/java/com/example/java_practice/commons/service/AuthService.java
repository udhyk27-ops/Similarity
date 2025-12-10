package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.UserStats;
import com.example.java_practice.commons.dto.UserWithAuth;

public interface AuthService {
    // 회원 정보 + 권한
    UserWithAuth getUserInfoById(String id);
    // 최근 로그인 등록
    void updateLastLoginByUserNo(int userNo);
    // 대시보드 회원 현황
    UserStats selectUserSortStats();
}
