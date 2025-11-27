package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface NoticeMapper {
    void insertNotice(Notice notice);
    void insertNoticeFile(NoticeFile noticeFile);
    ArrayList<Notice> selectNoticeList(Map<String, Object> params);
    int selectNoticeCnt();
    void updateViewCnt(@Param("noticeId") int id);
    Notice selectNoticeDetailById(@Param("noticeId") int id);
    ArrayList<NoticeFile> selectNoticeFilesByNoticeId(@Param("noticeId") int id);

}
