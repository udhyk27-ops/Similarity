package com.example.java_practice.commons.dto;

import lombok.Data;

@Data
public class AwardSearch {
    private String sort;
    private int page;
    private String keyword;
    private String filter;
    private String startDate;
    private String endDate;
}
