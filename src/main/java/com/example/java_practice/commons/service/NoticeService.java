package com.example.java_practice.commons.service;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeFile;
import com.example.java_practice.commons.dto.NoticeSearch;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public interface NoticeService {
    // 공지 등록
    Notice insertNotice(Notice notice);
    // 공지 등록 - 파일 첨부
    void insertNoticeFile(int noticeId, List<MultipartFile> noticeFiles);
    // 공지리스트
    ArrayList<Notice> selNoticeList(NoticeSearch noticeSearch, int page, int size);
    // 전체 공지 수(페이징용)
    int selNoticeCnt(NoticeSearch noticeSearch);
    // 공지사항 보기
    Notice selectNoticeDetailById(int noticeId);
    // 조회수 증가
    void updateViewCnt(int noticeId);
    // 공지사항 첨부파일
    ArrayList<NoticeFile> selectNoticeFilesByNoticeId(int noticeId);
    // 공지사항 삭제
    boolean deleteNoticeById(int noticeId);
    // 공지사항 수정
    Notice updateNoticeById(Notice notice);
    // 파일 전체 삭제
    void deleteNoticeFilesById(int fileId);
}
