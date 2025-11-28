package com.example.java_practice.commons.dto;

import lombok.Data;

@Data
public class UserWithAuth {
    private int f_user_no;
    private String f_id;
    private String f_name;
    private String f_sort;
    private String f_auth_award;
    private String f_auth_invit;
    private String f_auth_reg;
    private String f_auth_sim;
    private String f_auth_user;
    private String f_auth_admin;
}
