package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.UserRegStats;
import com.example.java_practice.commons.dto.UserStats;
import com.example.java_practice.commons.dto.UserWithAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapper {
    UserWithAuth selectUserById(@Param("userId") String id);
    void updateLastLoginByUserNo(@Param("userNo") int userNo);
    UserStats selectUserSortStats();
    List<UserRegStats> selectUserRegStats();
}
