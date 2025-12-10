package com.example.java_practice.commons.dto;

import lombok.Data;

@Data
public class UserStats {
    // 회원 현황(운영회원/관리자)
    private int user_cnt;
    private int admin_cnt;
}
