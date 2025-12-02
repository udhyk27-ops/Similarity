package com.example.java_practice.commons.dto;

import lombok.Data;

@Data
public class WorkSearch {
    private String staDate;
    private String endDate;
    private String staYear;
    private String endYear;
    private String schKeyword;
    private String keyword;

    private int startRow;
    private int endRow;
}
