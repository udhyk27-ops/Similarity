package com.example.java_practice.commons.dto;

import lombok.*;

/**
 * f_work_no 작품번호
 * f_title 제목
 * f_author 작가
 * f_year 출품년도
 * f_contest 공모전
 * f_award 상권명
 * f_code 작품코드
 * f_manager 주관
 * f_host 주최
 * f_reg_date 등록일시
 * f_filepath 파일경로
 * f_user_no 등록자 번호
 * f_work_size 사이즈
 * f_filename 파일명
 * f_memo 회원메모
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Award {
    private int f_work_no;
    private String f_title;
    private String f_author;
    private String f_year;
    private String f_contest;
    private String f_award;
    private String f_code;
    private String f_manager;
    private String f_host;
    private String f_reg_date;
    private String f_filepath;
    private int f_user_no;
    private String f_work_size;
    private String f_filename;
    private String f_memo;
}
