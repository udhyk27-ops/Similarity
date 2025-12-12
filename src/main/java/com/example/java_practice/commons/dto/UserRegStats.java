package com.example.java_practice.commons.dto;

import lombok.Getter;

/**
 * dashboard - 회원별 등록 수(회원 현황)
 * */
@Getter
public class UserRegStats {
    private String f_name;
    private int reg_cnt;
}
