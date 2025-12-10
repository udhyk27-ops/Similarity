package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.UserStats;
import com.example.java_practice.commons.dto.UserWithAuth;
import com.example.java_practice.commons.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;

    @Override
    public UserWithAuth getUserInfoById(String id) {
        return authMapper.selectUserById(id);
    }

    @Override
    @Transactional
    public void updateLastLoginByUserNo(int userNo) {
        authMapper.updateLastLoginByUserNo(userNo);
    }

    @Override
    public UserStats selectUserSortStats() {
        return authMapper.selectUserSortStats();
    }
}
