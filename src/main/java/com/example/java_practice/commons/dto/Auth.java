package com.example.java_practice.commons.dto;

import lombok.*;

/**
 * f_user_no 회원번호
 * f_auth_award 수상작 등록관리
 * f_auth_invit 초대작 등록관리
 * f_auth_reg 등록작품 현황
 * f_auth_sim 유사도 검색
 * f_auth_user 운영회원 관리
 * f_auth_admin 관리자 등록관리
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Auth {
    private Integer f_user_no;
    private String f_auth_award;
    private String f_auth_invit;
    private String f_auth_reg;
    private String f_auth_sim;
    private String f_auth_user;
    private String f_auth_admin;
}
