package com.example.java_practice.commons.dto;

import lombok.Getter;

/**
 * dashboard - 수상작/초대작 등록 현황
 * */
@Getter
public class WorkStats {
    private int total_cnt;
    /** 승인 */
    private int valid_cnt;
    /** 미승인 */
    private int invalid_cnt; 
}
