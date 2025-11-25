package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    User getUserInfoById(String id);
}
