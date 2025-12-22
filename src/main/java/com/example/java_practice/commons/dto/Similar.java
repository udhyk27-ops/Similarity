package com.example.java_practice.commons.dto;

import lombok.*;

/**
 * f_code 작품코드
 * f_title 작품명
 * f_author 작가
 * f_contest 공모전
 * f_award 상
 * f_filename 파일명
 * f_filepath 파일경로
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Similar {
    private String f_code;
    private String f_title;
    private String f_author;
    private String f_contest;
    private String f_award;
    private String f_filename;
    private String f_filepath;
}
