package com.example.java_practice.commons.dto;

import lombok.*;

@Getter
@Setter
public class NoticeSearch {
    private String staDate;
    private String endDate;
    private String keyword;
    // 페이징
    private int startRow;
    private int endRow;
}
