package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.User;
import com.example.java_practice.commons.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;

    @Override
    public User getUserInfoById(String id) {
        return authMapper.selectUserById(id);
    }
}
