package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.UserRegStats;
import com.example.java_practice.commons.dto.UserStats;
import com.example.java_practice.commons.dto.UserWithAuth;
import com.example.java_practice.commons.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<UserRegStats> selectUserRegStats() {
        return authMapper.selectUserRegStats();
    }
}
