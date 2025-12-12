package com.example.java_practice.commons.dto;

import lombok.Getter;

/**
 * dashboard - 운영회원/관리자 비율(회원 현황)
 * */
@Getter
public class UserStats {
    private int user_cnt;
    private int admin_cnt;
}
