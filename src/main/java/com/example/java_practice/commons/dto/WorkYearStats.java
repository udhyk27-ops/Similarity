package com.example.java_practice.commons.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 발매년도별 등록 수
 * */
@Getter
@Setter
public class WorkYearStats {
    private String f_year;
    private int award_cnt;
    private int invit_cnt;
}
