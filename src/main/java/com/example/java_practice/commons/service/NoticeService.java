package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Notice;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface NoticeService {
    // 공지 등록
    Notice insertNotice(Notice notice);
    // 공지 등록 - 파일 첨부
    void insertNoticeFile(int noticeId, List<MultipartFile> noticeFiles);
    // 공지리스트
    ArrayList<Notice> selNoticeList();

}
