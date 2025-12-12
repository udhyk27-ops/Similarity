package com.example.java_practice.commons.enums;

/**
 * 엑셀 저장
 * String sheetName : 엑셀 파일명
 * String[] headers : 헤더
 * String[] fieldNames : DB필드명
 * */
public enum WorkExcelType {
    AWARD(
            "수상작_등록현황",
            new String[]{"번호", "이미지", "제목", "저작자명", "공모전명", "상권명", "주최", "주관", "발표년도", "등록일시"},
            new String[]{"f_work_no", "f_filename", "f_title", "f_author", "f_contest", "f_award", "f_host", "f_manager", "f_year", "f_reg_date"}
    ),
    INVITE(
            "초대작_등록현황",
            new String[]{"번호", "이미지", "제목", "저작자명", "코드", "발표년도", "등록일시"},
            new String[]{"f_work_no", "f_filename", "f_title", "f_author", "f_code", "f_year", "f_reg_date"}
    );

    private final String sheetName;
    private final String[] headers;
    private final String[] fieldNames;

    WorkExcelType(String sheetName, String[] headers, String[] fieldNames) {
        this.sheetName = sheetName;
        this.headers = headers;
        this.fieldNames = fieldNames;
    }

    public String getSheetName() { return sheetName; }
    public String[] getHeaders() { return headers; }
    public String[] getFieldNames() { return fieldNames; }

    public static WorkExcelType fromString(String type) {
        return "award".equals(type) ? AWARD : INVITE;
    }

}
