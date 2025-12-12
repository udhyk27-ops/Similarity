package com.example.java_practice.commons.dto;

import lombok.Getter;

/**
 * session
 * */
@Getter
public class UserWithAuth {
    private int f_user_no;
    private String f_id;
    private String f_name;

    /** 회원 or 관리자 */
    private String f_sort;

    /**
     * 관리자 등록 관리에서 설정한 권한
     * f_auth_award : 수상작 등록관리
     * f_auth_invit : 초대작 등록관리
     * f_auth_reg : 등록작품현황
     * f_auth_sim : 유사도검색
     * f_auth_user : 운영회원관리
     * f_auth_admin : 관리자등록관리
     * */
    private String f_auth_award;
    private String f_auth_invit;
    private String f_auth_reg;
    private String f_auth_sim;
    private String f_auth_user;
    private String f_auth_admin;
}
