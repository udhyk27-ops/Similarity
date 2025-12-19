package com.example.java_practice.commons.dto;

import lombok.Data;

/**
 * sort => 수상작 / 초대작 구분
 * page => 페이징
 * keyword => 검색어
 * filter => select box 필터
 * startDate => 시작일자
 * endDate => 종료일자
 */
@Data
public class Search {
    private String sort;
//    private int offset;
//    private int limit;;
    private Integer offset;
    private Integer limit;
    private int page;
    private String keyword;
    private String schFilter;
    private String startDate;
    private String endDate;
    private String filter;
}
