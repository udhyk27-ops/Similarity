package com.example.java_practice.commons.dto;

import lombok.Data;

@Data
public class Award {
    private int id;
    private String title;
    private String author;
    private String contest;
    private String prize;
    private String code;
    private String regDate;
}
