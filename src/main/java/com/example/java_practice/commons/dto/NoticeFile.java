package com.example.java_practice.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFile {
    private int f_id;
    private int f_notice_id;
    private String f_filename;
    private String f_ori_filename;
    private String f_filepath;
    private String f_filesize;
}
