package com.example.java_practice.commons.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class WorkYearStats {
    // 발매년도별 등록 수
    private String f_year;
    private int award_cnt;
    private int invit_cnt;
}
