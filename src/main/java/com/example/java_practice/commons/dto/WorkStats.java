package com.example.java_practice.commons.dto;

import lombok.Data;

@Data
public class WorkStats {
    // 대시보드 등록 현황(수상작/초대작)
    private int total_cnt;
    private int valid_cnt; // 승인
    private int invalid_cnt; // 미승인
}
