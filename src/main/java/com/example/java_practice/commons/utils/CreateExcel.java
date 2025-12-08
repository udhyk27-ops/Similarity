package com.example.java_practice.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class CreateExcel {

    public static Workbook createWorkListExcel(String sheetName, String[] headers, String[] fieldNames, List<?> workList) throws IOException
    {
        // 엑셀 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 헤더 스타일 설정
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // 헤더 행 설정
        Row headerRow = sheet.createRow(0);
        for(int i = 0; i< headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 데이터행 생성
        int rowNom = 1;
        for(Object work : workList) {
            Row row = sheet.createRow(rowNom++);
            for(int i = 0; i< fieldNames.length; i++) {
                Cell cell = row.createCell(i);
                String fieldName = fieldNames[i];

                try{
                    // get + 필드명
                    String getMethod = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                    // getter 메서드 호출해서 값 가져옴
                    Method getter = work.getClass().getMethod(getMethod);
                    Object value = getter.invoke(work);

                    if(value != null) {
                        // 일반 텍스트
                        if(value instanceof Number) cell.setCellValue(((Number)value).doubleValue());
                        else if(value instanceof Boolean) cell.setCellValue((Boolean)value);
                        else cell.setCellValue(value.toString());

                    }else{
                        cell.setCellValue("");
                    }
                }catch (NoSuchMethodException e) {
                    // Getter 메서드가 존재하지 않는 경우 (예: 필드명이 잘못된 경우)
                    cell.setCellValue("ERROR: Getter not found");
                    System.err.println("Getter 메서드를 찾을 수 없습니다: " + fieldName);
                } catch (Exception e) {
                    // 기타 Reflection 또는 호출 오류
                    cell.setCellValue("ERROR: Data Access Failed");
                    log.error(e.getMessage(), e);
                }
            }
        }

        // 컬럼 너비 자동 조정
        for(int i = 0; i< headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
    // 양식 다운로드
    public static Workbook createFormExcel(String sheetName, String[] headers) throws IOException
    {
        // 엑셀 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 헤더 스타일 설정
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // 헤더 행 설정
        Row headerRow = sheet.createRow(0);
        for(int i = 0; i< headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 컬럼 너비 자동 조정
        for(int i = 0; i< headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }

}