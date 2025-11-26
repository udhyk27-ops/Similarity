package com.example.java_practice.commons.mapper;

import com.example.java_practice.commons.dto.Notice;
import com.example.java_practice.commons.dto.NoticeFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface NoticeMapper {

    void insertNotice(Notice notice);
    void insertNoticeFile(NoticeFile noticeFile);
    ArrayList<Notice> selectNoticeList();
    void updateViewCnt(@Param("noticeId") int id);
}
