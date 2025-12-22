package com.example.java_practice.commons.dto;

import lombok.Data;
import java.util.List;

/**
 * f_user_no 회원번호
 * f_id 아이디
 * f_password 비밀번호
 * f_name 이름
 * f_personal_num 주민번호
 * f_reg_date 등록일자
 * f_area 지역
 * f_birth 생년월일
 * f_phone 전화번호
 * f_email 이메일
 * f_dept 부서
 * f_position 직책
 * f_main_address 주소
 * f_sub_address 상세주소
 * f_login_date 최근 로그인 일자
 * f_mod_date 수정일자
 * f_status 상태
 * f_memo 회원메모
 * f_sort 회원 / 관리자
 * f_auth 권한
 */
@Data
public class User {
    private Integer f_user_no;
    private String f_id;
    private String f_password;
    private String f_name;
    private String f_personal_num;
    private String f_reg_date;
    private String f_area;
    private String f_birth;
    private String f_phone;
    private String f_email;
    private String f_dept;
    private String f_position;
    private String f_main_address;
    private String f_sub_address;
    private String f_login_date;
    private String f_mod_date;
    private String f_status;
    private String f_memo;
    private String f_sort;
    private List<Auth> f_auth;
}