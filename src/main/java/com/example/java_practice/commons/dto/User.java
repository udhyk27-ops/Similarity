package com.example.java_practice.commons.dto;

import lombok.Data;
import java.util.List;

@Data
public class User {
    private int f_user_no;
    private String f_id;
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