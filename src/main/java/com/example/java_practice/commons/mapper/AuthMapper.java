package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.UserWithAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    UserWithAuth selectUserById(@Param("userId") String id);
    void updateLastLoginByUserNo(@Param("userNo") int userNo);
}
