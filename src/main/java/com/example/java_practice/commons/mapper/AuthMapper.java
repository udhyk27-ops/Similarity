package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    User selectUserById(@Param("userId") String id);
}
