package com.example.java_practice.commons.enums;

public enum FormExcelType {
    AWARD_FORM(
            "수상작_리스트_등록양식",
            new String[]{"파일명", "공모전명", "저작자명", "상권명", "제목", "발표년도", "저작물사이즈(너비, 높이)", "주최", "주관"}
    ),
    INVITE_FORM(
            "초대작_리스트_등록양식",
            new String[]{"파일명", "저작자명", "제목", "발표년도", "저작물사이즈(너비, 높이)"}
    );
    private final String sheetName;
    private final String[] headers;

    FormExcelType(String sheetName, String[] headers){
        this.sheetName = sheetName;
        this.headers = headers;
    }

    public String getSheetName() { return sheetName; }
    public String[] getHeaders() { return headers; }

    public static FormExcelType fromString(String type) {
        return "award".equals(type) ? AWARD_FORM : INVITE_FORM;
    }
}
