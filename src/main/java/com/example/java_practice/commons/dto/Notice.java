package com.example.java_practice.commons.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notice {
    private int f_id;
    private String f_title;
    private String f_content;
    private int f_regid;
    private LocalDateTime f_regdate;
    private int f_modiid;
    private LocalDateTime f_modidate;
    private int f_viewcnt;

    private User writer;
}
